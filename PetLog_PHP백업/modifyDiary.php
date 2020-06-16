<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
    if (!$link)  
    {  
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();  
    }  
    mysqli_query($link, 'set names utf8');

    $title=$_POST['title'];
    $contents=$_POST['contents'];
    $mood=$_POST['mood'];
    $diaryid=$_POST['diaryid'];
    $picChanged=$_POST['picchanged'];
    $file= $_FILES['image'];

    if ($picChanged == 'true') {
        
        $sql="select diary_media from diary where diary_id = '$diaryid'";
        $result=mysqli_query($link, $sql); 
        if($result){
            while($row=mysqli_fetch_array($result)) {
                $data=$row[0];
            }
        }
        unlink("./$data");

        $srcName= $file['name'];
        $tmpName= $file['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
        
        //임시 저장소 이미지를 원하는 폴더로 이동
        //print_r($_FILES);exit();
        $dstName= "mypetimg/".date('Ymd_his').$srcName;
        $result=move_uploaded_file($tmpName, $dstName);
            
        if($result){
            echo "upload success\n";
        }else{
            echo "upload fail\n";
        }

        try{
            // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
                                                 //여긴 컬럼이름           //자바에서 선언한 변수
            $stmt = $con->prepare('UPDATE diary SET diary_title = (:title), diary_contents = (:contents), diary_mood = (:mood), diary_media = (:imgurl) WHERE diary_id = (:diaryid)');
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':contents', $contents);
            $stmt->bindParam(':mood', $mood);
            $stmt->bindParam(':imgurl', $dstName);
            $stmt->bindParam(':diaryid', $diaryid);
    
            if($stmt->execute())
            {
                $successMSG = "일기를 수정했습니다.";
            }
            else
            {
                $errMSG = "일기 등록 에러";
            }
    
        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }

    } else {
        try{
            // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
                                                 //여긴 컬럼이름           //자바에서 선언한 변수
            $stmt = $con->prepare('UPDATE diary SET diary_title = (:title), diary_contents = (:contents), diary_mood = (:mood) WHERE diary_id = (:diaryid)');
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':contents', $contents);
            $stmt->bindParam(':mood', $mood);
            $stmt->bindParam(':diaryid', $diaryid);
    
            if($stmt->execute())
            {
                $successMSG = "일기를 수정했습니다.";
            }
            else
            {
                $errMSG = "일기 등록 에러";
            }
    
        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }
    }

    /*$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $title=$_POST['title'];
        $contents=$_POST['contents'];
        $mood=$_POST['mood'];
        $diaryid=$_POST['diaryid'];
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('UPDATE diary SET diary_title = (:title), diary_contents = (:contents), diary_mood = (:mood) WHERE diary_id = (:diaryid)');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':contents', $contents);
                $stmt->bindParam(':mood', $mood);
                $stmt->bindParam(':diaryid', $diaryid);

                if($stmt->execute())
                {
                    $successMSG = "일기를 수정했습니다.";
                }
                else
                {
                    $errMSG = "일기 등록 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }*/

    mysqli_close($link);

?>
