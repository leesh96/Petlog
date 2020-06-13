<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $id=$_POST['id'];
    $title=$_POST['title'];
    $content=$_POST['contents'];
    $file= $_FILES['image'];

    $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
    if (!$link)  
    {  
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();  
    }
    $sql="select share_image from share_post where id = '$id'";
    $result=mysqli_query($link, $sql); 
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
    $dstName= "shareimg/".date('Ymd_his').$srcName;
    $result=move_uploaded_file($tmpName, $dstName);
        
    if($result){
        echo "upload success\n";
    }else{
        echo "upload fail\n";
    }

    try{
        // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
        $stmt = $con->prepare('UPDATE share_post SET title = (:title), content = (:content), share_image = (:imgurl) WHERE id = (:id)');
        $stmt->bindParam(':id', $id);
        $stmt->bindParam(':title', $title);
        $stmt->bindParam(':content', $content);
        $stmt->bindParam(':imgurl', $dstName);
                
        if($stmt->execute())
        {
            $successMSG = "게시물을 수정했습니다.";
        }
        else
        {
            $errMSG = "게시물 등록 에러";
        }

        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }

?>
