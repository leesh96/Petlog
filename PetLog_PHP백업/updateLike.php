<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
        $nickname=$_POST['nickname'];
        $id=$_POST['postid'];
        $likecnt=$_POST['likecnt'];
        $isliked=$_POST['isliked'];

            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('UPDATE petsta_post SET like_cnt = (:likecnt) WHERE post_id = (:id)');
                $stmt->bindParam(':likecnt', $likecnt);
                $stmt->bindParam(':id', $id);

                if($stmt->execute())
                {
                    $successMSG = "좋아요수 업데이트";
                }
                else
                {
                    $errMSG = "실패";
                }
                
                $stmt = $con->prepare('UPDATE petsta_like SET islike = (:isliked) WHERE nickname = (:nickname) and post_id = (:id)');
                $stmt->bindParam(':isliked', $isliked);
                $stmt->bindParam(':nickname', $nickname);
                $stmt->bindParam(':id', $id);

                if($stmt->execute())
                {
                    $successMSG = "좋아요 상태 업데이트";
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