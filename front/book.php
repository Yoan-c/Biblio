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
        'password' => $_SESSION['userToken'],
    ];
    $result =  getinfo($fields, $path);
    echo $result;
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
  ?>


  <?php


    function getinfo($fields, $path){
      $postdata = http_build_query($fields);
      $ch = curl_init();
      curl_setopt($ch,CURLOPT_URL, $path);
      curl_setopt($ch,CURLOPT_POST, true);
      curl_setopt($ch,CURLOPT_POSTFIELDS, $postdata);
      curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
      $result = curl_exec($ch);
    }
    echo $result;
  }

  ?>
