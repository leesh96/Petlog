<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // �ȵ���̵� �ڵ��� postParameters ������ ������ �̸��� ������ ���� ���� �޽��ϴ�.
	$nickname=$_POST['nickname'];
        $comment=$_POST['comment'];
	$title=$_POST['title'];
	//$walkimage=$_POST['walkimage'];

	//���������� �˷���.
        if(empty($comment)){ 
            $errMSG = "����� �Է��ϼ���.";
        }
        if(!isset($errMSG)) //���� �Է��� �Ǿ��ٸ� 
        {
            try{
                // SQL���� �����Ͽ� �����͸� MySQL ������ walk comment���̺� �����մϴ�. 
							//���� �÷��̸�                 //�ڹٿ��� ������ ����

                $stmt = $con->prepare('INSERT INTO walk_comment(comment,nickname,title) VALUES(:comment,:nickname,:title)');
		$stmt->bindParam(':comment', $comment);
              	$stmt->bindParam(':nickname', $nickname);
                $stmt->bindParam(':title', $title);
                //$stmt->bindParam(':walkimage', $walkimage);


                if($stmt->execute())
                {
                    $successMSG = "���ο� ��� �ۼ��߽��ϴ�.";
                }
                else
                {
                    $errMSG = "��� �ۼ� ����";
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
  