<%@include file="header.jsp" %>

<main class="container_connect">
    <div class="error_co">
        <c:if test="${ is_creat == true }">
            <p><c:out value="${value}"/></p>
            <p>Une erreur Reussi</p>

        </c:if>
        <c:if test="${is_creat == false}">
        <p>Une erreur est surevenu veuillez vous reconnecter</p>

        </c:if>
    </div>
    <form class="form_connect" method="post" action="askConnexion">
        <div class="label_form_co">
            <label for="mail" class="id_email_co"> E-mail :</label>
            <input id="mail" type="text" name="mail"/>
        </div>
        <div class="label_form_co">
            <label for="password" class="id_mdp_co"> Mot de passe :</label>
            <input id="password" type="password" name="password"/>
        </div>
        <div class="label_form_co btn_form_co">
            <button class="link">Connexion</button>
        </div>

    </form>
    <ul>
        <c:forEach var="utilisateur" items="${ name }">
            <li><c:out value="${ utilisateur }" /> <c:out value="${ utilisateur.firstName }" /></li>
        </c:forEach>
        <c:forEach var="utilisateur" items="${ name2 }">
            <li><c:out value="${ utilisateur.lastName }" /> <c:out value="${ utilisateur.firstName }" /></li>
        </c:forEach>
    </ul>
</main>

<%@include file="footer.jsp" %>