<?php
    $con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    mysqli_query($con,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    
    /* $statement = mysqli_query($con, "SELECT * FROM user_info WHERE u_id = '{$userID}' AND u_pw = '{$userPassword}'"); */
    $statement = mysqli_prepare($con, "SELECT * FROM user_info WHERE u_id = ? AND u_pw = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userName, $userNick, $userBdy, $userIntro, $userFace, $follow_cnt);

    $response = array();
    $response["success"] = false;
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userName"] = $userName;
        $response["userNick"] = $userNick;
        $response["profileImg"] = $userFace;    
    }
 
    echo json_encode($response);

?>
