<%@include file="header.jsp" %>

<main class="container_connect">
    <div class="error_co">
        <p>Une erreur est surevenu veuillez vous reconnecter</p>
    </div>
    <form class="form_connect">
        <div class="label_form_co">
            <label for="mail" class="id_email_co"> E-mail :</label>
            <input id="mail" type="text" name="mail"/>
        </div>
        <div class="label_form_co">
            <label for="mdp" class="id_mdp_co"> Mot de passe :</label>
            <input id="mdp" type="password" name="mdp"/>
        </div>
        <div class="label_form_co btn_form_co">
            <button class="link">Connexion</button>
        </div>

    </form>
</main>

<%@include file="footer.jsp" %>