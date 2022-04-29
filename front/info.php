<?php
  require_once("./function/function.php");
  session_start();

  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else {
    $result = getCompte();
      $result = json_decode($result, true, 6);

    if ($result && $result["isConnect"] === "true")
    {
        $user = json_decode($result["user"]);

    }
  ?>
    <?php include'./header_co.php';?>
    <main>
        <section class="info">
                <table>
                    <tr><td>Nom : </td> <td> <?php echo $user[0]->{"lastName"}?> </td></tr>
                    <tr><td>Prénom : </td> <td> <?php echo $user[0]->{"firstName"}?></td></tr>
                    <tr><td>E-mail : </td> <td> <?php echo $user[0]->{"email"}?> </td></tr>
                    <tr><td colspan="2" class="center">
                        <div class="link link_modif">
                            <a href="./modifierCompte.php" class="">Modifier les informations</a>
                        </div>
                    </td></tr>
                </table>
        </section>
    </main>
    <?php
    include './scriptJS.php';
    include './footer.php';?>
  <?php
  }
  ?>
