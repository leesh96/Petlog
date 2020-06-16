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
                $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
                if (!$link)  
                {  
                    echo "MySQL 접속 에러 : ";
                    echo mysqli_connect_error();
                    exit();  
                }
                mysqli_query($link, 'set names utf8');

                $sql="select share_image from share_post where id = '$id'";
                $result=mysqli_query($link, $sql); 
                if($result){
                    while($row=mysqli_fetch_array($result)) {
                        $data=$row[0];
                    }
                }
                unlink("./$data");
                // SQL문을 실행하여 데이터를 MySQL 서버의 테이블에 저장합니다. 
							                        //여긴 컬럼이름           //자바에서 선언한 변수
                $stmt = $con->prepare('DELETE FROM share_post WHERE id = (:id)');
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