<?php
  session_start();
  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else {
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
    //$result =  getinfo($fields, $path);
    $result = json_decode($result);
    if($result->{'isConnect'} === "true" && $_SESSION['userToken'] === $result->{'token'})
    {
  ?>
    <?php include'./header_co.php';?>
    <main class="welcome">
        <p>
            Bienvenue sur "Biblio" le site de la bilbiothèque de "Pontault-Combault"
        </p>
    </main>
    <?php include './footer.php';?>
    <?php
      }
      else {
        header('Location: ./connexion.php');
        exit();
      }
    }
    ?>
