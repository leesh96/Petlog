<?php
    $con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    mysqli_query($con, 'SET NAMES utf8');

    
    
$result=mysql_query("select * from walkimagetest",$con);
$cnt=0;
$arr=array();    
while($row=mysql_fetch_array($result)){
        
    $count=$cnt;        
    $arr[$count]['IMAGE']=base64_encode($row[1]);
    $arr[$count]['NO']=$row[0];
    $cnt++;
}
print(json_encode($arr));

  ?>