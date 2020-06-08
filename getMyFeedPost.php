<?php  
$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  
mysqli_set_charset($link,"utf8");

$follower_jsonlist = json_decode($_POST['follower'], true);
$data = array();
try {
    for($i = 0; $i < count($follower_jsonlist); $i++) {
        $nickname = $follower_jsonlist[$i]['follower_nick'];
        $sql = "select * from petsta_post where nickname='$nickname'";
        $result = mysqli_query($link, $sql);
        if($result) {
            while($row=mysqli_fetch_array($result)) {
                array_push($data, 
                    array('id'=>$row[0],
                    'nickname'=>$row[1],
                    'imgurl'=>$row[2],
                    'contents'=>$row[3],
                    'tag'=>$row[4],
                    'time'=>$row[5],
                    'likecnt'=>$row[6],
                    'commentcnt'=>$row[7]
                ));
            }
        }
        /* if($stmt->execute())
        {
            $successMSG = "";
        }
        else
        {
            $errMSG = "삭제실패";
        } */
    }
}
/* 
$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'nickname'=>$row[1],
            'imgurl'=>$row[2],
            'contents'=>$row[3],
            'tag'=>$row[4],
            'time'=>$row[5],
            'likecnt'=>$row[6],
            'commentcnt'=>$row[7]
        ));
    }
 */
    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("myfeed"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 

mysqli_close($link);

?>
