<?php
	$host='localhost';
	$uname='dongmin';
	$pwd='dongmin1234';
	$db="petlog";

$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	
	
	$imagedevice=$_GET['image'];
		
	$data=base64_decode($imagedevice);
	
	$escaped_values=mysql_real_escape_string($data);
	
	$r=mysql_query("insert into walkimagetest (IMAGE) value('$escaped_values')",$con);

	
	print(json_encode($r));
	mysql_close($con);
?>