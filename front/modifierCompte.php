<?php
  require_once("./function/function.php");
  session_start();

  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else {
    $result = getinfo();
      $result = json_decode($result, true, 6);


    if ($result && $result["isConnect"] === "true")
    {
        $user = json_decode($result["user"]);
    }
  ?>
    <?php include'./header_co.php';?>


<main class="container_connect">
    <div class="error_creat" id="errorModif">

                <p class="resModifSuccess hide" id="resModif"> Infomation changé avec succes </p>

                <p class="errorModifMsg hide" id="resModif"> Erreur , aucun changement n'a été effectué </p>

    </div>
    <form class="form_creat" method="POST" action="" id="formModif">
        <div class="label_form_creat">
            <label for="lastNameModif" class="form_creat_space">Nom :</label>
            <input id="lastNameModif" type="text" name="lastName" value="<?php echo $user[0]->{"lastName"}?>">
        </div>
        <div class="label_form_creat">
            <label for="firstNameModif" class="form_creat_space">Prenom :</label>
            <input id="firstNameModif" type="text" name="firstName" value="<?php echo $user[0]->{"firstName"}?>"/>
        </div>
        <div class="label_form_creat">
            <label for="mailModif" class="form_creat_space"> E-mail :</label>
            <input id="mailModif" type="mail" name="mail" value="<?php echo $user[0]->{"email"}?>"/>
        </div>
        <div class="label_form_creat">
            <label for="mdpModif" class="form_creat_space">Nouveau mot de passe :</label>
            <input id="mdpModif" type="password" name="mdp"/>
        </div>
        <div class="label_form_creat">
            <label for="mdpConfirmModif" class="form_creat_space"> Confirmation du mot de passe :</label>
            <input id="mdpConfirmModif" type="password" name="mdpConfirm"/>
            <input type="hidden" value="<?php echo $_SESSION['userToken'];?>" name="token" id="token"/>
            <input type="hidden" value="<?php echo $_SESSION['loggued_on_userMail'];?>" name="mailCompte" id="mailCompte"/>
        </div>
        <div class="btn_form_creat">
            <button class="link" id="btnValidModif">Valider</button>
        </div>
    </form>
</main>

<?php
include './scriptJS.php';
include './footer.php';?>
<?php
}
?>
