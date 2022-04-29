<?php

function affiche(){
  echo "test";
}
function getResult(){
    $path= "http://localhost:8080/json/home";
    $fields = [
        'mail' => $_SESSION['loggued_on_userMail'],
        'token' => $_SESSION['userToken'],
    ];
    $postdata = http_build_query($fields);
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL, $path);
    curl_setopt($ch,CURLOPT_POST, true);
    curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    return $result;
}

function getLend(){
    $path= "http://localhost:8080/json/lend";
    $fields = [
        'mail' => $_SESSION['loggued_on_userMail'],
        'token' => $_SESSION['userToken'],
    ];
    $postdata = http_build_query($fields);
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL, $path);
    curl_setopt($ch,CURLOPT_POST, true);
    curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    return $result;
}
function getCompte(){
    $path= "http://localhost:8080/json/info";
    $fields = [
        'mail' => $_SESSION['loggued_on_userMail'],
        'token' => $_SESSION['userToken'],
    ];
    $postdata = http_build_query($fields);
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL, $path);
    curl_setopt($ch,CURLOPT_POST, true);
    curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    return $result;
}

function getinfo(){
    $path= "http://localhost:8080/json/update";
    $fields = [
        'mail' => $_SESSION['loggued_on_userMail'],
        'token' => $_SESSION['userToken'],
    ];
    $postdata = http_build_query($fields);
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL, $path);
    curl_setopt($ch,CURLOPT_POST, true);
    curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    return $result;
}

 ?>
