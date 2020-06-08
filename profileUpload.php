<?php
    header("Content-Type:text/html; charset=UTF-8");
    error_reporting(E_ALL);
    ini_set("display_errors", 1);

    $nickname=$_POST['nickname'];
    $file= $_FILES['image'];
 
    //이미지 파일을 영구보관하기 위해
    //이미지 파일의 세부정보 얻어오기
    $srcName= $file['name'];
    $tmpName= $file['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
 
    //임시 저장소 이미지를 원하는 폴더로 이동
    //print_r($_FILES);exit();
    $dstName= "diaryimg/".date('Ymd_his').$srcName;
    $result=move_uploaded_file($tmpName, $dstName);
    if($result){
        echo "upload success\n";
    }else{
        echo "upload fail\n";
    }

    // $name, $msg, $dstName, $now DB에 저장
    // MySQL에 접속
    $conn= mysqli_connect("localhost","suho","suho1234","petlog");

    //한글 깨짐 방지
    mysqli_query($conn, "set names utf8");

    //insert하는 쿼리문
    $sql="update petsta_profile set profile_image = '$dstName' where nickname = '$nickname'";

    $result =mysqli_query($conn, $sql); //쿼리를 요청하다. 

    if($result) echo "insert success \n";
    else echo "insert fail \n";

    mysqli_close($conn);

?>