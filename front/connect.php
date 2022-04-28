<?php
session_start();
if(isset($_POST)){
  var_dump($_POST);

  $fields = [
      'mail' => $_POST['mail'],
      'password' => $_POST['password'],
  ];
  $postdata = http_build_query($fields);
  $ch = curl_init();
  curl_setopt($ch,CURLOPT_URL, 'http://localhost:8080/json/askConnexion');
  curl_setopt($ch,CURLOPT_POST, true);
  curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
  curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
  $result = curl_exec($ch);

  echo $result;
    $result = json_decode($result);
    if($result->{'isConnect'}== false){
      echo "AFIICHE FAUX";
    }
    if ($result && $result->{'isConnect'} === "true" ){
      $_SESSION['loggued_on_userMail'] = $_POST['mail'];
      $_SESSION['userToken'] = $result->{'token'};
      header('Location: ./home.php');
      exit();
    }
    else {
      header('Location: ./connexion.php');
      exit();
    }
}

?>
