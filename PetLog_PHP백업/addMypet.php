<?php
    $con = mysqli_connect("localhost", "dongmin", "dongmin1234", "petlog");
    mysqli_query($con, 'SET NAMES utf8');

    $petName = $_POST["petName"];
    $petSex = $_POST["petSex"];
    $petSpecies = $_POST["petSpecies"];
    $petAge = $_POST["petAge"];
    $petBday = $_POST["petBday"];
    $petOwner = $_POST["petOwner"];

    $petFace = $_FILES["petFace"];
    
    //이미지 파일을 영구보관하기 위해
    //이미지 파일의 세부정보 얻어오기
    $srcName= $petFace['name'];
    $tmpName= $petFace['tmp_name']; //php 파일을 받으면 임시저장소에 넣는다. 그곳이 tmp
 
    //임시 저장소 이미지를 원하는 폴더로 이동
    //print_r($_FILES);exit();
    $dstName= "mypetimg/".date('Ymd_his').$petOwner.$srcName;
    $result=move_uploaded_file($tmpName, $dstName);
    if($result){
        echo "upload success\n";
    }else{
        echo "upload fail\n";
    }
    
    $statement = mysqli_prepare($con, "INSERT INTO mypet_info (pet_name, pet_sex, pet_species, pet_age, pet_bday, pet_face, pet_owner) VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssssss", $petName, $petSex, $petSpecies, $petAge, $petBday, $dstName, $petOwner);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);

?>