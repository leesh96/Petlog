<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}
mysqli_set_charset($link,"utf8"); 
$nickname = $_POST['nickname'];
$sql="select * from follow where user_nickname='$nickname' and isfollow=1";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('follower'=>$row[2]
        ));
    }
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("follow"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
}
 
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>
