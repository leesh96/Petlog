<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $nickname=$_POST['nickname'];
        $intro=$_POST['intro'];
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('UPDATE user_info SET u_intro = (:intro) WHERE u_nickname = (:nickname)');
                $stmt->bindParam(':intro', $intro);
                $stmt->bindParam(':nickname', $nickname);

                if($stmt->execute())
                {
                    $successMSG = "소개글 업데이트.";
                }
                else
                {
                    $errMSG = "실패";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }

?>