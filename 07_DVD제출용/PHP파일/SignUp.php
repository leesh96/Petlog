<?php

$con=mysqli_connect("localhost","dongmin","dongmin1234","petlog");

mysqli_set_charset($con,"utf8");



if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//변수 	       소스에서 입력한 값 저장
$username = $_POST['Name'];
$userid = $_POST['Id'];
$userpw = $_POST['Pw'];
$usernickname = $_POST['Nick'];
$userbdy = $_POST['Bdy'];
						//데이터베이스에 있는 컬럼이름

$result = mysqli_query($con,"insert into user_info (u_name, u_id ,u_pw, u_nickname, u_bdy) values ('$username','$userid','$userpw','$usernickname','$userbdy')");



  if($result){

    echo '회원가입에 성공하셨습니다.';

  }

  else{

    echo '회원가입에 실패했습니다.';

  }



mysqli_close($con);

?>

