<%@include file="header.jsp" %>

<main>
    <section class="sect_search">
        <div class="logo_search" id="logo_search">
        <img src="../../ressources/img/icon_search.PNG" alt="icon de recherche" width="20px"/>
        <hr/>
    </div>
        <div class="search">
        <div class="sect_search_champs" id="sect_search_champs">
            <h1 class="hide">Recherche </h1>
            <form class="form_search">
                <label for="search_title">Titre :</label>
                <input type="text" id="search_title" class="search_input_book"/>
                <label for="search_auteur">Auteur :</label>
                <input type="text" id="search_auteur" class="search_input_book"/>
                <label for="search_genre">Genre :</label>
                <select name="sect_genre" id="search_genre" class="search_genre">
                    <option value="Manga">Manga</option>
                    <option value="SF">Science-Fiction</option>
                    <option value="Action">Action</option>
                </select>
                <label for="search_langue">Langue :</label>
                <select name="sect_langue" id="search_langue" class="search_langue">
                    <option value="en">Anglais</option>
                    <option value="fr">Français</option>
                </select>
                <div class="pos_btn_search">
                    <button class="btn_search">Lancer la recherche</button>
                </div>
            </form>
        </div>
            <div class="content_book">
                <div class="card_book" id="book_1">
                    <div class="left_card">
                        <img src="../../ressources/img/45080.jpg" alt="image book" width="120">
                    </div>
                    <div class="right_card">
                        <p class="hide">Titre :</p>
                        <p>Les pierres du cauchemar</p>
                        <p class="hide">Genre :</p>
                        <p>Manga</p>
                        <button class="btn_info_book" id="show_book" onclick="showModal()">Afficher</button>
                    </div>

                </div>
                <div class="card_book">
                    <div class="left_card">
                        <img src="../../ressources/img/45080.jpg" alt="image book" width="120">
                    </div>
                    <div class="right_card">
                        <p class="hide">Titre :</p>
                        <p>Les pierres du cauchemar</p>
                        <p class="hide">Genre :</p>
                        <p>Manga</p>
                        <button class="btn_info_book">Afficher</button>
                    </div>

                </div>
                <div class="card_book">
                    <div class="left_card">
                        <img src="../../ressources/img/45080.jpg" alt="image book" width="120">
                    </div>
                    <div class="right_card">
                        <p class="hide">Titre :</p>
                        <p>Les pierres du cauchemar</p>
                        <p class="hide">Genre :</p>
                        <p>Manga</p>
                        <button class="btn_info_book">Afficher</button>
                    </div>

                </div>
                <div class="card_book">
                <div class="left_card">
                    <img src="../../ressources/img/45080.jpg" alt="image book" width="120">
                </div>
                <div class="right_card">
                    <p class="hide">Titre :</p>
                    <p>Les pierres du cauchemar</p>
                    <p class="hide">Genre :</p>
                    <p>Manga</p>
                    <button class="btn_info_book">Afficher</button>
                </div>

            </div>
            </div>

    </div>

    </section>
    <div id="modal_book" class="modal_book">
        <div id="modal_book_content" class="modal_book_content">
            <span class="close" onclick="hideModal()">&times</span>
            <div class="center_modal">
                <div class="left_modal">
                    <img src="../../ressources/img/4450593.jpg" alt="image book">
                </div>
                <div class="right_modal">
                    <p>Titre :</p>
                    <p>Last dance on the straight pier</p>
                    <p>Auteur :</p>
                    <p>Sarah Bird</p>
                    <p>Editeur :</p>
                    <p>Flammard</p>
                    <p>Genre :</p>
                    <p>Roman</p>
                    <p>Date de pubication</p>
                    <p>26/10/1989</p>
                    <p>ISBN :</p>
                    <p>xxx/x/xxxx/xxxx/x</p>
                    <p>Disponibilité :</p>
                    <p>20 disponibles</p>

                </div>
                <div class="desc_book_modal">
                    <h3>Présentation : </h3>
                    <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin aliquet tincidunt efficitur.
                        Vestibulum euismod facilisis laoreet. Ut id laoreet lacus. Sed eget libero ante. Phasellus id nisi ipsum.
                        Nunc auctor sollicitudin lacus vitae mattis.
                        Praesent pretium consequat magna, quis sollicitudin massa pulvinar et. Sed in tincidunt lorem.</p>

                </div>
                <div class="bottom_modal">
                    <button class="btn_reserver">Réserver</button>
                </div>
            </div>
        </div>
    </div>
</main>

<%@include file="footer.jsp" %>