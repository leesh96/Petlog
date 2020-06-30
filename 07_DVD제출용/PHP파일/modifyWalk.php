<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
    if (!$link)  
    {  
        echo "MySQL ���� ���� : ";
        echo mysqli_connect_error();
        exit();  
    }
    mysqli_query($link, 'set names utf8');

    $id=$_POST['id'];
    $title=$_POST['title'];
    $content=$_POST['content'];
    $picChanged=$_POST['picchanged'];
    $file= $_FILES['image'];

    if ($picChanged == 'true') {
        $sql="select walk_image from walk_post where id = '$id'";
        $result=mysqli_query($link, $sql); 
        if($result){
            while($row=mysqli_fetch_array($result)) {
                $data=$row[0];
            }
        }
        unlink("./$data");
    
        //�̹��� ������ ���������ϱ� ����
        //�̹��� ������ �������� ������
        $srcName= $file['name'];
        $tmpName= $file['tmp_name']; //php ������ ������ �ӽ�����ҿ� �ִ´�. �װ��� tmp
        
        //�ӽ� ����� �̹����� ���ϴ� ������ �̵�
        //print_r($_FILES);exit();
        $dstName= "shareimg/".date('Ymd_his').$srcName;
        $result=move_uploaded_file($tmpName, $dstName);
            
        if($result){
            echo "upload success\n";
        }else{
            echo "upload fail\n";
        }
        
        try{
            // SQL���� �����Ͽ� �����͸� MySQL ������ ���̺��� �����մϴ�. 
            $stmt = $con->prepare('UPDATE walk_post SET title = (:title), content = (:content), walk_image = (:imgurl) WHERE id = (:id)');                
            $stmt->bindParam(':id', $id );
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':content', $content);
            $stmt->bindParam(':imgurl', $dstName);
    
            if($stmt->execute())
            {
                $successMSG = "�Խù��� �����߽��ϴ�.";
            }
            else
            {
                $errMSG = "�Խù� ��� ����";
            }
    
        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }
    } else {
        try{
            // SQL���� �����Ͽ� �����͸� MySQL ������ ���̺��� �����մϴ�. 
            $stmt = $con->prepare('UPDATE walk_post SET title = (:title), content = (:content) WHERE id = (:id)');                
            $stmt->bindParam(':id', $id );
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':content', $content);
    
            if($stmt->execute())
            {
                $successMSG = "�Խù��� �����߽��ϴ�.";
            }
            else
            {
                $errMSG = "�Խù� ��� ����";
            }
    
        } catch(PDOException $e) {
            die("Database error: " . $e->getMessage()); 
        }
    }
    

?>
