<%@include file="header_co.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
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

                    </select>
                    <label for="search_langue">Langue :</label>
                    <select name="sect_langue" id="search_langue" class="search_langue">

                    </select>
                    <div class="pos_btn_search">
                        <button class="btn_search" id="getSearch" onclick="getSearch()">Lancer la recherche</button>
                    </div>
                </div>
            </div>
            <div class="content_book">


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
<c:set var="json_text" value="${booksInfo}"/>
<script>
    var jsonObj = ${json_text};
    console.log(jsonObj[0]);
</script>
<%@include file="footer.jsp" %>