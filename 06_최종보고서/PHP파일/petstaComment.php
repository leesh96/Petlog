<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
	$postid=$_POST['postid'];
	$nickname=$_POST['nickname'];
    $comment=$_POST['comment'];

	//디버깅용으로 알려줌.
        if(empty($comment)){ 
            $errMSG = "댓글을 입력하세요.";
        }
        if(!isset($errMSG)) //내용 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 share comment테이블에 저장합니다. 
							//여긴 컬럼이름                 //자바에서 선언한 변수
                $stmt = $con->prepare('INSERT INTO petsta_comments(post_id,comment,nickname) VALUES(:postid,:comment,:nickname)');
                //$stmt = $con->prepare('INSERT INTO share_comment_test(comment,nickname,title) VALUES(:comment,:nickname,:title)');
                $stmt->bindParam(':postid', $postid);
		        $stmt->bindParam(':comment', $comment);
              	$stmt->bindParam(':nickname', $nickname);

                if($stmt->execute())
                {
                    $successMSG = "새로운 게시물 추가했습니다.";
                }
                else
                {
                    $errMSG = "게시물 추가 에러";
                }
                
                $stmt = $con->prepare('UPDATE petsta_post SET comment_cnt = comment_cnt+1 WHERE post_id = (:postid)');
                //$stmt = $con->prepare('INSERT INTO share_comment_test(comment,nickname,title) VALUES(:comment,:nickname,:title)');
                $stmt->bindParam(':postid', $postid);

                if($stmt->execute())
                {
                    $successMSG = "댓글수 + 1";
                }
                else
                {
                    $errMSG = "댓글수+1에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
?>
  