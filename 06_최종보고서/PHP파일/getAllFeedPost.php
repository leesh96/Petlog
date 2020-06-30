<?php  
$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

$align = $_POST['align'];
$postnick=$_POST['nickname'];

mysqli_set_charset($link,"utf8"); 

switch($align) {
    case "":
        $sql = "select * from petsta_post order by write_time DESC";
    break;
    case "like":
        $sql = "select * from petsta_post order by like_cnt DESC, write_time DESC";
    break;
    case "10":
    case "11":
    case "12":
    case "13":
    case "14":
    case "15":
    case "16":
    case "20":
    case "21":
    case "22":
    case "23":
    case "24":
    case "25":
    case "26":
        $sql = "select * from petsta_post WHERE tag = '$align' order by like_cnt DESC, write_time DESC";
    break;
    default :
        $sql = "select * from petsta_post order by write_time DESC";
    break;
}


$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    while($row=mysqli_fetch_array($result)){
        $facesql = "select u_face from user_info where u_nickname='$row[1]'";
        $tmpface = mysqli_query($link, $facesql);
        $facerow = mysqli_fetch_array($tmpface);
        $likesql = "select islike from petsta_like where post_id='$row[0]' AND nickname='$postnick'";
        $tmplike = mysqli_query($link, $likesql);
        $likerow = mysqli_fetch_array($tmplike);
        array_push($data, 
            array('id'=>$row[0],
                'nickname'=>$row[1],
                'imgurl'=>$row[2],
                'contents'=>$row[3],
                'tag'=>$row[4],
                'time'=>$row[5],
                'likecnt'=>$row[6],
                'commentcnt'=>$row[7],
                'writerface'=>$facerow[0],
                'islike'=>$likerow[0]
        ));
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("allpost"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 

mysqli_close($link);

?>