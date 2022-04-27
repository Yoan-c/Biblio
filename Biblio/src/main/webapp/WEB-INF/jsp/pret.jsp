<%@include file="header_co.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<main class="main_book">
    <div class="main_section">
        <div class="book_lend">
            <c:choose>
                <c:when test="${sizeList >= 0}">
                    <c:forEach begin="0" end="${sizeList}" varStatus="vs">
                        <section>
                            <div class="img_book">
                                <img src="<c:out value="${listPret[vs.count -1][0].couverture}"/>" alt="<c:out value="${vs.count}"/>" width="120">
                            </div>
                            <div class="info_book">
                                <div class="desc_book">
                                    <p>Titre : <c:out value="${listPret[vs.count -1][0].titre}"/></p>
                                    <p>Auteur :
                                        <c:forEach begin="0" end="${listPret[vs.count -1][2][0].nb}"  var="aut" items="${listPret[1][2]}" varStatus="vs1">
                                            <c:out value="${aut.firstName}"/>
                                            <c:out value="${aut.lastName}"/>
                                            <c:choose>
                                                <c:when test="${vs1.first}">
                                                </c:when>
                                                <c:when test="${vs1.last}">
                                                </c:when>
                                                <c:otherwise>
                                                    ,
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </p>
                                    <p> Genre :
                                        <c:forEach begin="0" end="${listPret[vs.count -1][3][0].nb}"  var="genre" items="${listPret[vs.count -1][3]}" varStatus="vs2">
                                            <c:out value="${genre.nomGenre}"/>
                                            <c:choose>
                                                <c:when test="${vs2.first}">
                                                </c:when>
                                                <c:when test="${vs2.last}">
                                                </c:when>
                                                <c:otherwise>
                                                    ,
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </p>
                                    <p>Editeur : <c:out value="${listPret[vs.count -1][1][0]}"/></p>
                                    <p>Date de publication : <c:out value="${fn:substring(listPret[vs.count -1][0].date_publication, 0,19)}"/></p>
                                    <p>ISBN : <c:out value="${listPret[vs.count -1][0].isbn}"/></p>
                                    <p>Début du prêt : <c:out value="${fn:substring(listPret[vs.count -1][0].date_debut, 0,19)}"/> </p>
                                    <p>Fin du prêt : <c:out value="${fn:substring(listPret[vs.count -1][0].date_fin, 0,19)}"/></p>

                                </div>

                                <div class="reserve_book">
                                    <c:choose>
                                        <c:when test = "${listPret[vs.count -1][0].renouvellement == true}">
                                            <button class="btn_reserve_book inactive_lend" onclick="Pro_Impossible()">Impossible de prolonger la période la période de prêt une seconde fois</button>
                                        </c:when>

                                        <c:when test = "${listPret[vs.count -1][0].renouvellement == false}">
                                            <button class="btn_reserve_book active_lend" onclick="Pro_book(${listPret[vs.count-1][0].isbn})">Prolonger la période de pret</button>
                                        </c:when>

                                    </c:choose>
                                </div>
                            </div>
                        </section>
                    </c:forEach>
                </c:when>
                <c:when test="${sizeList < 0}">
                    <div>
                        <p>
                            aucun prêt en cours
                        </p>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</main>

<%@include file="footer.jsp" %>