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

$sql="select follower_nickname from follow where user_nickname = '$nickname' and isfollow=1";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        $tmpsql="select u_face from user_info where u_nickname = '$row[0]'";
        $tmpres=mysqli_query($link,$tmpsql);
        $tmprow=mysqli_fetch_array($tmpres);
        array_push($data,
        array(
            'follower_nickname'=>$row[0],
            'follower_face'=>$tmprow[0]
        ));
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("following"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 

mysqli_close($link);  

?>
