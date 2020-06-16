<?php
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $nickname = $_POST['nickname'];
    //$nickname = '한찬웅';

    $link=mysqli_connect("localhost","dongmin","dongmin1234", "petlog" );  
    if (!$link)  
    {  
        echo "MySQL 접속 에러 : ";
        echo mysqli_connect_error();
        exit();  
    } 
    mysqli_query($link, 'set names utf8');

    $sql="select u_face from user_info where u_nickname = '$nickname'";
    $result=mysqli_query($link, $sql);
    
    if($result){
        while($row=mysqli_fetch_array($result)) {
            $data=$row[0];
            echo $data;
        }
    }
    unlink("./$data");
        
    $delsql="update user_info set u_face=NULL where u_nickname = '$nickname'";
    $result=mysqli_query($link, $delsql); 
?>
