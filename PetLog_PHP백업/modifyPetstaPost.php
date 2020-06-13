<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $contents=$_POST['contents'];
    $tag=$_POST['tag'];
    $postid=$_POST['id'];
    $file= $_FILES['image'];

    $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
    if (!$link)  
    {  
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();  
    }  

    $sql="select petsta_image from petsta_post where post_id = '$postid'";
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
    $dstName= "petstaimg/".date('Ymd_his').$srcName;
    $result=move_uploaded_file($tmpName, $dstName);
        
    if($result){
        echo "upload success\n";
    }else{
        echo "upload fail\n";
    }

        try{
            // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                     //여긴 컬럼이름           //자바에서 선언한 변수
            $stmt = $con->prepare('UPDATE petsta_post SET petsta_image=(:imgurl), contents=(:contents), tag=(:tag)  WHERE post_id = (:id)');
            $stmt->bindParam(':imgurl', $dstName);
            $stmt->bindParam(':contents', $contents);
            $stmt->bindParam(':tag', $tag);
            $stmt->bindParam(':id', $postid);

            if($stmt->execute())
            {
                $successMSG = "게시물 수정했습니다.";
            }
            else
            {
                $errMSG = "게시물 수정 에러";
            }

        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
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

?>