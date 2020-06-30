<?php
    
    $con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    mysqli_query($con,'SET NAMES utf8');

    $id = $_POST["id"];
    $title = $_POST["title"];

    $statement = mysqli_prepare($con, "UPDATE share_post SET title= ? WHERE id = ?");
    mysqli_stmt_bind_param($statement, "ss", $title , $id);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
 
    echo json_encode($response);

?>
