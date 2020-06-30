<?php
    header("Content-Type:text/html; charset=UTF-8");
    error_reporting(E_ALL);
    ini_set("display_errors", 1);

    $nickname=$_POST['nickname'];
    $file= $_FILES['image'];
 
    // MySQL에 접속
    $conn= mysqli_connect("localhost","suho","suho1234","petlog");
    if (!$conn)  
    {  
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();  
    }
    //한글 깨짐 방지
    mysqli_query($conn, "set names utf8");

    $sql="select u_face from user_info where u_nickname = '$nickname'";
    $result=mysqli_query($conn, $sql); 
    if($result){
        while($row=mysqli_fetch_array($result)) {
            $data=$row[0];
        }
    }
    unlink("./$data");

    //이미지 파일을 영구보관하기 위해
    //이미지 파일의 세부정보 얻어오기
    $srcName= $file['name'];
    $tmpName= $file['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
 
    //임시 저장소 이미지를 원하는 폴더로 이동
    //print_r($_FILES);exit();
    $dstName= "profileimg/".$nickname."_".$srcName;
    $result=move_uploaded_file($tmpName, $dstName);
    /* if($result){
        echo "upload success\n";
    }else{
        echo "upload fail\n";
    } */

    // $name, $msg, $dstName, $now DB에 저장

    //insert하는 쿼리문
    $sql="update user_info set u_face = '$dstName' where u_nickname = '$nickname'";

    $result =mysqli_query($conn, $sql); //쿼리를 요청하다. 

    /* if($result) echo "insert success \n";
    else echo "insert fail \n"; */

    $sql="select * from user_info where u_nickname='$nickname'";

    $result =mysqli_query($conn, $sql);
    $data = array(); 
    if($result) {
        while($row=mysqli_fetch_array($result)){
            array_push($data, 
                array('userFace'=>$row[6],
                
            ));
        }
        //echo "<pre>"; print_r($data); echo '</pre>';
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode($data, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

    }
    else echo "fail \n";

    mysqli_close($conn);

?>
