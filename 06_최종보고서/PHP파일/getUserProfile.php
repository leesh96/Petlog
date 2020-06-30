<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

$usernick=$_POST['usernick'];
$mynick=$_POST['mynick'];

$sql="select u_intro, u_face, follower_cnt from user_info where u_nickname = '$usernick'";

$result=mysqli_query($link,$sql);
$data = array();

if($result){

    while($row=mysqli_fetch_array($result)) {
        $tmpsql="select isfollow from follow where user_nickname='$mynick' and follower_nickname='$usernick'";
        $tmpres = mysqli_query($link, $tmpsql);
        $tmprow = mysqli_fetch_array($tmpres);
        $data["intro"]=$row[0];
        $data["imgurl"]=$row[1];
        $data["followercnt"]=$row[2];
        $data["isfollow"]=$tmprow[0];
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
