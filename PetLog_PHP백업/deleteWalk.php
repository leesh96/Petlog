<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // �ȵ���̵� �ڵ��� postParameters ������ ������ �̸��� ������ ���� ���� �޽��ϴ�.

        $id=$_POST['id'];
        
            try{
                $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
                if (!$link)  
                {  
                    echo "MySQL ���� ���� : ";
                    echo mysqli_connect_error();
                    exit();  
                }
                mysqli_query($link, 'set names utf8');
                $sql="select walk_image from walk_post where id = '$id'";
                $result=mysqli_query($link, $sql); 
                if($result){
                    while($row=mysqli_fetch_array($result)) {
                        $data=$row[0];
                    }
                }
                unlink("./$data");
                // SQL���� �����Ͽ� �����͸� MySQL ������ ���̺��� �����մϴ�. 
							                        //���� �÷��̸�           //�ڹٿ��� ������ ����
                $stmt = $con->prepare('DELETE FROM walk_post WHERE id = (:id)');
                $stmt->bindParam(':id', $id);

                if($stmt->execute())
                {
                    $successMSG = "��������";
                }
                else
                {
                    $errMSG = "��������";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
    }

?>