<?php include'./header.php';?>

<main class="container_connect">
    <div class="error_creat">
        <p id="msg_err_creat" class="hide">Erreur veuillez vous retenter plus tard</p>
    </div>
    <div class="form_creat">
        <div class="label_form_creat">
            <label for="lastName" class="form_creat_space">Nom :</label>
            <input id="lastName" type="text" name="lastName"/>
        </div>
        <div class="label_form_creat">
            <label for="firstName" class="form_creat_space">Prenom :</label>
            <input id="firstName" type="text" name="firstName"/>
        </div>
        <div class="label_form_creat">
            <label for="mail_create" class="form_creat_space"> E-mail :</label>
            <input id="mail_create" type="text" name="mail"/>
        </div>
        <div class="label_form_creat">
            <label for="password_create" class="form_creat_space"> Mot de passe :</label>
            <input id="password_create" type="password" name="password"/>
        </div>
        <div class="label_form_creat">
            <label for="password_confirm" class="form_creat_space"> Confirmation du mot de passe :</label>
            <input id="password_confirm" type="password" name="password_confirm"/>
        </div>
        <div class="btn_form_creat">
            <button class="link" onclick="createUser()">Valider</button>
        </div>

    </div>
</main>

<?php
  include './scriptJS.php';
  include './footer.php';
?>