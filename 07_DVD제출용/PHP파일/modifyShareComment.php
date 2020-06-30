<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $id=$_POST['id'];
    $comment=$_POST['comment'];

    try{
        // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
					                                    //여긴 컬럼이름           //자바에서 선언한 변수
        $stmt = $con->prepare('UPDATE share_comment SET comment=(:comment) WHERE id = (:id)');
        $stmt->bindParam(':comment', $comment);
        $stmt->bindParam(':id', $id);

        if($stmt->execute())
        {
            $successMSG = "댓글 수정했습니다.";
        }
        else
        {
            $errMSG = "댓글 수정 에러";
        }
    } catch(PDOException $e) {
        die("Database error: " . $e->getMessage()); 
    }

?>