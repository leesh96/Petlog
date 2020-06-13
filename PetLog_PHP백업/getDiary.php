
<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

//$userid = 'lksy1294@naver.com';
$userid = $_POST['userid'];
$picdate = $_POST['picdate'];
//$picdate = '';

if($picdate != '') {
    $sql="select * from diary where diary_owner = '$userid' and diary_writedate = '$picdate' order by diary_writedate DESC";
}
else {
    $sql="select * from diary where diary_owner = '$userid' order by diary_writedate DESC";
}


$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'title'=>$row[2],
            'contents'=>$row[3],
            'imgurl'=>$row[4],
            'mood'=>$row[5],
            'date'=>$row[6]
        ));
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("diary"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>