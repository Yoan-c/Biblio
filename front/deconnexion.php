<?php
  session_start();
  echo "isset".isset($_SESSION);
  unset($_SESSION['userToken']);
  unset($_SESSION['loggued_on_userMail']);
  session_destroy();
  header('Location: ./connexion.php');
  exit();
?>
