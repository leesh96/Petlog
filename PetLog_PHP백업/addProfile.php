<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $contents=$_POST['contents'];
        $nickname=$_POST['nickname'];
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('INSERT INTO petsta_profile(profile_content, nickname) VALUES(:contents, :nickname)');
                $stmt->bindParam(':contents', $contents);
                $stmt->bindParam(':nickname', $nickname);

                if($stmt->execute())
                {
                    $successMSG = "프로필을 등록했습니다..";
                }
                else
                {
                    $errMSG = "프로필 등록 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }

?>