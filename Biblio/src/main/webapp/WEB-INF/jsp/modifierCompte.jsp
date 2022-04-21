<%@include file="header_co.jsp" %>


<main class="container_connect">
    <div class="error_creat">
        <p>Une erreur est surevenu lors de la modification</p>
    </div>
    <form class="form_creat">
        <div class="label_form_creat">
            <label for="name_create" class="form_creat_space">Nom :</label>
            <input id="name_create" type="text" name="name"/>
        </div>
        <div class="label_form_creat">
            <label for="surname_creat" class="form_creat_space">Prenom :</label>
            <input id="surname_creat" type="text" name="surname_create"/>
        </div>
        <div class="label_form_creat">
            <label for="mail_create" class="form_creat_space"> E-mail :</label>
            <input id="mail_create" type="text" name="mail"/>
        </div>
        <div class="label_form_creat">
            <label for="mdp_create" class="form_creat_space"> Mot de passe :</label>
            <input id="mdp_create" type="password" name="mdp"/>
        </div>
        <div class="label_form_creat">
            <label for="mdp_confirm" class="form_creat_space"> Confirmation du mot de passe :</label>
            <input id="mdp_confirm" type="password" name="mdp_confirm"/>
        </div>
        <div class="btn_form_creat">
            <button class="link">Valider</button>
        </div>

    </form>
</main>

<%@include file="footer.jsp" %>