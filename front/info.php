<?php
  session_start();
  /*echo "isset".isset($_SESSION);
  unset($_SESSION['userToken']);
  unset($_SESSION['loggued_on_userMail']);
  session_destroy();
    echo "isset".isset($_SESSION['userToken']);
  */
  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else {
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
