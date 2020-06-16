<?php 
    $con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    mysqli_query($con, 'set names utf8');

    $userNickname = $_POST["userNickname"];
   
    $statement = mysqli_prepare($con, "SELECT u_nickname FROM user_info WHERE u_nickname = ?");
    mysqli_stmt_bind_param($statement, "s", $userNickname);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userNickname);


    $response = array();
    $response["success"] = true;
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = false;
        $response["userNickname"] = $userNickname;
    }
   
    echo json_encode($response);


?>