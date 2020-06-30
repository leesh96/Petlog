<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $usernick=$_POST['usernick'];
        $mynick=$_POST['mynick'];
        $followcnt=$_POST['followcnt'];
        $isfollowed=$_POST['isfollowed'];

            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('UPDATE user_info SET follower_cnt = (:followcnt) WHERE u_nickname = (:usernick)');
                $stmt->bindParam(':followcnt', $followcnt);
                $stmt->bindParam(':usernick', $usernick);

                if($stmt->execute())
                {
                    $successMSG = "구독자수 업데이트";
                }
                else
                {
                    $errMSG = "실패";
                }

                $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
                if (!$link)  
                {  
                    echo "MySQL 접속 에러 : ";
                    echo mysqli_connect_error();
                    exit();  
                } 
                mysqli_query($link, 'set names utf8');
                
                $sql = "insert into follow (user_nickname, follower_nickname, isfollow) values ('$mynick', '$usernick', '$isfollowed')";

                $result=mysqli_query($link, $sql);

                if($result)
                {
                    $successMSG = "구독 상태 추가";
                }
                else
                {
                    $errMSG = "실패";
                }

                mysqli_close($link);

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }

?>
