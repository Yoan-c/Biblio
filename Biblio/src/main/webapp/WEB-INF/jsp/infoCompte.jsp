<%@include file="header_co.jsp" %>

<main>
    <section class="info">
            <table>
                <tr><td>Nom : </td> <td> <c:out value="${infoCompte[0].lastName}"/> </td></tr>
                <tr><td>Prénom : </td> <td> <c:out value="${infoCompte[0].firstName}"/> </td></tr>
                <tr><td>E-mail : </td> <td> <c:out value="${infoCompte[0].email}"/> </td></tr>
                <tr><td colspan="2" class="center">
                    <div class="link link_modif">
                        <a href="update" class="" >Modifier les informations</a>
                    </div>
                </td></tr>
            </table>
    </section>
</main>
<%@include file="footer.jsp" %>