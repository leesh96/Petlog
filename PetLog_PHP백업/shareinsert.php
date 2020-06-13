<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.
	    $nickname=$_POST['nickname'];
        $title=$_POST['title'];
        $content=$_POST['contents'];
        $file= $_FILES['image'];
    
        //이미지 파일을 영구보관하기 위해
        //이미지 파일의 세부정보 얻어오기
        $srcName= $file['name'];
        $tmpName= $file['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
    
        //임시 저장소 이미지를 원하는 폴더로 이동
        //print_r($_FILES);exit();
        $dstName= "shareimg/".date('Ymd_his').$srcName;
        $result=move_uploaded_file($tmpName, $dstName);
        if($result){
            echo "upload success\n";
        }else{
            echo "upload fail\n";
        }

        //디버깅용으로 알려줌.
        if(empty($title)){ 
            $errMSG = "제목을 입력하세요.";
        }
        else if(empty($content)){
            $errMSG = "내용을 입력하세요.";
        }

        if(!isset($errMSG)) // 제목 내용 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 share post테이블에 저장합니다. 
							//여긴 컬럼이름                 //자바에서 선언한 변수
                $stmt = $con->prepare('INSERT INTO share_post(nickname,title, content,share_image) VALUES(:nickname,:title, :content,:shareimage)');
              	$stmt->bindParam(':nickname', $nickname);
		        $stmt->bindParam(':title', $title);
                $stmt->bindParam(':content', $content);
                $stmt->bindParam(':shareimage', $dstName);


                if($stmt->execute())
                {
                    $successMSG = "새로운 게시물 추가했습니다.";
                }
                else
                {
                    $errMSG = "게시물 추가 에러";
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
  