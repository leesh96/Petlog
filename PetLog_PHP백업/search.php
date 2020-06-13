<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');



//POST 값을 읽어온다.
$content=isset($_POST['content']) ? $_POST['content'] : '';
$title = isset($_POST['title']) ? $_POST['title'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($content != "" ){ 

    $sql="select * from share_post where content='$content' and title='$title'";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "'";
        echo $content,", ",$title;
        echo "'은 찾을 수 없습니다.";
    }
	else{

   		$data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        	extract($row);

            array_push($data, 
                array('id'=>$row["id"],
                'title'=>$row["title"],
                'content'=>$row["content"]
            ));
        }


        if (!$android) {
            echo "<pre>"; 
            print_r($data); 
            echo '</pre>';
        }else
        {
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        }
    }
}
else {
    echo "검색할 내용을 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         내용: <input type = "text" name = "content" />
         제목: <input type = "text" name = "title" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>