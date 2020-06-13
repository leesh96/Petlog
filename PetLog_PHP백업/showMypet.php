<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

$userid = $_POST['userid'];
   
$sql="select * from mypet_info where pet_owner = '$userid'";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'name'=>$row[2],
            'sex'=>$row[3],
            'specie'=>$row[4],
            'age'=>$row[5],
            'bday'=>$row[6],
            'memo'=>$row[7],
            'face'=>$row[8]
        ));
    }

    //echo "<pre>"; print_r($data); echo '</pre>';
header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("mypet"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>