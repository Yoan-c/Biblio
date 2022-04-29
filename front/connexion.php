<?php include'./header.php';?>

<main class="container_connect">
    <div class="error_co">
    </div>
    <form id="form_connect" class="form_connect" method="post" action="connect.php" name="formConnect">
        <div class="label_form_co">
            <label for="mail" class="id_email_co"> E-mail :</label>
            <input id="mail" type="text" name="mail"/>
        </div>
        <div class="label_form_co">
            <label for="password" class="id_mdp_co"> Mot de passe :</label>
            <input id="password" type="password" name="password"/>
        </div>
        <div class="label_form_co btn_form_co">
            <input type=submit class="link" id="btnFormConnect" name="connexion" value="Connexion" />
        </div>

    </form>
</main>
<script type="text/javascript">
  var b = "";
</script>
<?php
  include './footer.php';
  include './scriptJS.php';
?>
