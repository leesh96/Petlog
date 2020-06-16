<?php  

$link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

//$nickname = '관리자';

$sql="select * from walk_post where nickname='관리자' order by walk_date DESC";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'title'=>$row[1],
            'content'=>$row[2],
	    'nickname'=>$row[3],
	    'date'=>$row[5],
	    'walkimage'=>$row[4],
	    'position'=>$row[6],
		'posx'=>$row[7],
		'posy'=>$row[8]
        ));
    }
    //echo "<pre>"; print_r($data); echo '</pre>';
    $allsql="select * from walk_post where nickname!='관리자' order by walk_date DESC";
    $allres=mysqli_query($link,$allsql);
    while($allrow=mysqli_fetch_array($allres)){
        array_push($data, 
            array('id'=>$allrow[0],
            'title'=>$allrow[1],
            'content'=>$allrow[2],
            'nickname'=>$allrow[3],
            'date'=>$allrow[5],
            'walkimage'=>$allrow[4],
            'position'=>$allrow[6],
            'posx'=>$allrow[7],
            'posy'=>$allrow[8]
        ));
    }

    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("dongmin"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
}

 
mysqli_close($link);  
   
?>
