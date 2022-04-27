<%@include file="header_co.jsp" %>

<main>
    <section class="sect_search">
        <div class="logo_search" id="logo_search">
        <img src="../../ressources/img/icon_search.PNG" alt="icon de recherche" width="20px"/>
        <hr/>
    </div>
        <div class="search">
        <div class="sect_search_champs" id="sect_search_champs">
            <h1 class="hide">Recherche </h1>
            <div class="form_search">
                <label for="search_title">Titre :</label>
                <input type="text" id="search_title" class="search_input_book"/>
                <label for="search_auteur">Auteur :</label>
                <input type="text" id="search_auteur" class="search_input_book"/>
                <label for="search_genre">Genre :</label>
                <select name="sect_genre" id="search_genre" class="search_genre">
                    <c:forEach begin="0" end="${genreSize -1 }" varStatus="vsG">
                        <option value="${genre[vsG.count -1].genre}"><c:out value="${genre[vsG.count -1].genre}"/></option>
                    </c:forEach>
                </select>
                <label for="search_langue">Langue :</label>
                <select name="sect_langue" id="search_langue" class="search_langue">
                    <c:forEach begin="0" end="${langueSize -1 }" varStatus="vsL">
                        <option value="${langue[vsL.count -1].nom}"><c:out value="${langue[vsL.count -1].nom}"/></option>
                    </c:forEach>

                </select>
                <div class="pos_btn_search">
                    <button class="btn_search" id="getSearch" onclick="getSearch()">Lancer la recherche</button>
                </div>
            </div>
        </div>
            <div class="content_book">
                <c:forEach begin="0" end="${booksSize -1 }" varStatus="vs">

                    <div class="card_book" id="book_1">
                        <div class="left_card">
                            <img src="<c:out value='${booksInfo[vs.count -1 ][0].cover}'/>" alt="image book" width="120">
                        </div>
                        <div class="right_card">
                            <p><c:out value="${booksInfo[vs.count-1][0].title}"></c:out></p>
                            <p><c:out value="${booksInfo[vs.count-1][4]}"/></p>
                            <button class="btn_info_book" id="${'show_book'.concat(vs.count)}" onclick="showModal('${booksInfo[vs.count-1][0]}', '${booksInfo[vs.count-1][1]}','${booksInfo[vs.count-1][2]}', '${booksInfo[vs.count-1][3]}', '${booksInfo[vs.count-1][4]}')">Afficher</button>
                        </div>
                    </div>
                </c:forEach>
            </div>

    </div>

    </section>
    <div id="modal_book" class="modal_book">
        <div id="modal_book_content" class="modal_book_content">
            <span class="close" onclick="hideModal()">&times</span>
            <div class="center_modal" id="idCenterModal">

                <div class="left_modal" id="left_modal">
                </div>
                <div class="right_modal" id="right_modal">

                </div>
                <div class="desc_book_modal" id="idDescModal">
                </div>
                <div class="bottom_modal" id="idBtnModal">
                </div>
            </div>
        </div>
    </div>
</main>

<%@include file="footer.jsp" %>