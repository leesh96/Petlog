<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL ���� ���� : ";
    echo mysqli_connect_error();
    exit();  
}  

$postnick=$_POST['nickname'];
mysqli_set_charset($link,"utf8"); 


$sql="select * from share_post where nickname='$postnick' ";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'title'=>$row[1],
            'content'=>$row[2],
            'nickname'=>$row[3],
            'imgurl'=>$row[4],
	    'date'=>$row[5]
        ));
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("dongmin"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL�� ó���� ���� �߻� : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>
