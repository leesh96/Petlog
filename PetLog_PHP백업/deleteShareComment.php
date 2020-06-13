<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $id=$_POST['id'];
        
            try{
                $stmt = $con->prepare('DELETE FROM share_comment WHERE id = (:id)');
                $stmt->bindParam(':id', $id);

                if($stmt->execute())
                {
                    $successMSG = "삭제성공";
                }
                else
                {
                    $errMSG = "삭제실패";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }

?>
