<%@include file="header_co.jsp" %>


<main class="container_connect">
    <div class="error_creat" id="errorModif">
        <c:choose>
            <c:when test="${success == 2}">
                <p class="resModifSuccess" id="resModif"> Infomation changé avec succes </p>
            </c:when>

            <c:when test="${success == 1 }">
                <p class="errorModifMsg" id="resModif"> Erreur , aucun changement n'a été effectué </p>
            </c:when>
        </c:choose>
    </div>
    <form class="form_creat" method="post" action="updateCompte" id="formModif">
        <div class="label_form_creat">
            <label for="lastNameModif" class="form_creat_space">Nom :</label>
            <input id="lastNameModif" type="text" name="lastName" value="<c:out value='${infoCompte[0].lastName}'/>">
        </div>
        <div class="label_form_creat">
            <label for="firstNameModif" class="form_creat_space">Prenom :</label>
            <input id="firstNameModif" type="text" name="firstName" value="<c:out value='${infoCompte[0].firstName}'/>"/>
        </div>
        <div class="label_form_creat">
            <label for="mailModif" class="form_creat_space"> E-mail :</label>
            <input id="mailModif" type="mail" name="mail" value="<c:out value='${infoCompte[0].email}'/>"/>
        </div>
        <div class="label_form_creat">
            <label for="mdpModif" class="form_creat_space">Nouveau mot de passe :</label>
            <input id="mdpModif" type="password" name="mdp"/>
        </div>
        <div class="label_form_creat">
            <label for="mdpConfirmModif" class="form_creat_space"> Confirmation du mot de passe :</label>
            <input id="mdpConfirmModif" type="password" name="mdpConfirm"/>
        </div>
        <div class="btn_form_creat">
            <button class="link" id="btnValidModif">Valider</button>
        </div>
    </form>
</main>

<%@include file="footer.jsp" %>