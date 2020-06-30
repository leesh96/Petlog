<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $userid=$_POST['userid'];
    $nickname=$_POST['nickname'];

        try{
            // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                     //여긴 컬럼이름           //자바에서 선언한 변수
            $stmt = $con->prepare('UPDATE user_info SET u_nickname = (:nickname) WHERE u_id = (:userid)');
            $stmt->bindParam(':nickname', $nickname);
            $stmt->bindParam(':userid', $userid);

            if($stmt->execute())
            {
                $successMSG = "닉네임 수정 완료.";
            }
            else
            {
                $errMSG = "닉네임 수정 에러";
            }

        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }
?>