<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // �ȵ���̵� �ڵ��� postParameters ������ ������ �̸��� ������ ���� ���� �޽��ϴ�.
	$share_id=$_POST['share_id'];
	$nickname=$_POST['nickname'];
    $comment=$_POST['comment'];
	$title=$_POST['title'];

	//���������� �˷���.
        if(empty($comment)){ 
            $errMSG = "����� �Է��ϼ���.";
        }
        if(!isset($errMSG)) //���� �Է��� �Ǿ��ٸ� 
        {
            try{
                // SQL���� �����Ͽ� �����͸� MySQL ������ share comment���̺� �����մϴ�. 
							//���� �÷��̸�                 //�ڹٿ��� ������ ����
                $stmt = $con->prepare('INSERT INTO share_comment(share_id,comment,nickname,title) VALUES(:share_id,:comment,:nickname,:title)');
                //$stmt = $con->prepare('INSERT INTO share_comment_test(comment,nickname,title) VALUES(:comment,:nickname,:title)');
                $stmt->bindParam(':share_id', $share_id);
		        $stmt->bindParam(':comment', $comment);
              	$stmt->bindParam(':nickname', $nickname);
                $stmt->bindParam(':title', $title);


                if($stmt->execute())
                {
                    $successMSG = "���ο� �Խù� �߰��߽��ϴ�.";
                }
                else
                {
                    $errMSG = "�Խù� �߰� ����";
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
  