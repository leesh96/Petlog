<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//POST로 보낸 값을 받아서 변수에 입력합니다.
		$id = $_POST['id'];
		$title = $_POST['title'];
		$content = $_POST['content'];
		//$nickname = $_POST['nickname'];
		
		//DB 접속 스크립트를 불러옵니다.
		$con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    	mysqli_query($con, 'set names utf8');
		
		//정보를 수정하는 쿼리문을 작성합니다.
		$sql = "UPDATE walk_post SET title = '$title', content = '$content' WHERE id = $id;";
		
		//Updating database table
		if(mysqli_query($con,$sql)){
                        // 수정 성공 시 아래 내용을 출력합니다. 
			echo '정보가 성공적으로 수정되었습니다.';
		}else{
                        // 수정 실패 시 아래 내용을 출력합니다. 
			echo '정보를 수정할 수 없습니다.';
		}
		
		//접속을 종료합니다.
		mysqli_close($con);
	}
?>
