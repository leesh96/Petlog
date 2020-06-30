<?php
    header("Content-Type:text/html; charset=UTF-8");
    error_reporting(E_ALL);
    ini_set("display_errors", 1);

    $title=$_POST['title'];
    $contents=$_POST['contents'];
    $date=$_POST['writedate'];
    $mood=$_POST['mood'];
    $userid=$_POST['userid'];
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
    $sql="insert into diary (diary_owner, diary_title, diary_contents, diary_media, diary_mood, diary_writedate) values('$userid','$title','$contents', '$dstName', '$mood', '$date')";

    $result =mysqli_query($conn, $sql); //쿼리를 요청하다. 

    if($result) echo "insert success \n";
    else echo "insert fail \n";

    mysqli_close($conn);

    /*
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $title=$_POST['title'];
        $contents=$_POST['contents'];
        $date=$_POST['writedate'];
        $mood=$_POST['mood'];
        $userid=$_POST['userid'];
        $image=$_FILE['image'];

        //이미지 파일을 영구보관하기 위해
        //이미지 파일의 세부정보 얻어오기
        $srcName= $image['name'];
        $tmpName= $image['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
    
        //임시 저장소 이미지를 원하는 폴더로 이동
        $dstName= "diaryimg/".date('Ymd_his').$srcName;
        $result=move_uploaded_file($tmpName, $dstName);
        if($result){
            echo "upload success\n";
        }else{
            echo "upload fail\n";
        }

            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('INSERT INTO diary(diary_owner, diary_title, diary_contents, diary_media, diary_mood, diary_writedate) VALUES(:userid, :title, :contents, :media, :mood, :writedate)');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':contents', $contents);
                $stmt->bindParam(':userid', $userid);
                $stmt->bindParam(':media', $dstName);
                $stmt->bindParam(':mood', $mood);
                $stmt->bindParam(':writedate', $date);

                if($stmt->execute())
                {
                    $successMSG = "새로운 일기를 추가했습니다.";
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