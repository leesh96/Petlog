<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

$nickname=$_POST['nickname'];

$sql="select u_intro, u_face, follower_cnt from user_info where u_nickname = '$nickname'";

$result=mysqli_query($link,$sql);
$data = array();

if($result){

    while($row=mysqli_fetch_array($result)) {
        $data["intro"]=$row[0];
        $data["imgurl"]=$row[1];
        $data["followercnt"]=$row[2];
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode($data, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>