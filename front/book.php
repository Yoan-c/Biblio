<?php
  require_once("./function/function.php");
  session_start();
  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else {
    $result = getResult();
    if ($result){
      $result = json_decode($result, true, 6);
      $genre = json_decode($result["genre"]);
      $langue = json_decode($result["langue"]);
      $Lstbook = json_decode($result["book"]);

    }
    if($result["isConnect"] === "true" && $_SESSION['userToken'] === $result["token"])
    {
  ?>
    <?php include'./header_co.php';?>

      <main>
        <section class="sect_search">
            <div class="logo_search" id="logo_search">
            <img src="img/icon_search.PNG" alt="icon de recherche" width="20px"/>
            <hr/>
        </div>
            <div class="search" id="search">
            <div class="sect_search_champs" id="sect_search_champs">
                <h1 class="hide">Recherche </h1>
                <div class="form_search">
                    <label for="search_title">Titre :</label>
                    <input type="text" id="search_title" class="search_input_book"/>
                    <label for="search_auteur">Auteur :</label>
                    <input type="text" id="search_auteur" class="search_input_book"/>
                    <label for="search_genre">Genre :</label>
                    <select name="sect_genre" id="search_genre" class="search_genre">
                      <option value="" selected></option>
                      <?php
                        for ($i = 0; $i < sizeof($genre); $i++){
                      ?>
                              <option value="<?php echo ($genre[$i]->{"genre"});?>"><?php echo ($genre[$i]->{"genre"});?></option>
                        <?php
                        }
                        ?>
                    </select>
                    <label for="search_langue">Langue :</label>
                    <select name="sect_langue" id="search_langue" class="search_langue">
                      <option value="" selected></option>
                      <?php
                        for ($i = 0; $i < sizeof($langue); $i++){
                      ?>
                              <option value="<?php echo ($langue[$i]->{"nom"});?>"><?php echo ($langue[$i]->{"nom"});?></option>
                        <?php
                        }
                        ?>
                    </select>
                    <div class="pos_btn_search">
                        <button class="btn_search" id="getSearch" onclick="getSearch(1)">Lancer la recherche</button>
                        <input type="hidden" id="idToken" value="<?php echo$_SESSION['userToken'];?>"/>
                    </div>
                </div>
            </div>
                <div id="contentBook" class="content_book">

                  <?php
                    for ($i = 0; $i < sizeof($Lstbook); $i++){
                      $book = $Lstbook[$i];
                      $bookEncode = json_encode($book);
                  ?>

                  <div class="card_book" id="book_1">
                      <div class="left_card">
                          <img src="<?php echo $book[0]->{"cover"};?>" alt="image book" width="120"/>
                      </div>
                      <div class="right_card">
                          <p><?php echo $book[0]->{"title"};?></p>
                          <p><?php echo $book[4];?></p>
                          <button class="btn_info_book" id="<?php echo 'show_book'.$i;?>" onclick=showModal(<?php echo $i;?>)>Afficher</button>
                      </div>
                  </div>

                    <?php
                    }
                    ?>
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

      <script type="text/javascript">
        var listBook = <?php echo json_encode($Lstbook);?>
      </script>

    <?php
    include './scriptJS.php';
    include './footer.php';
    ?>
  <?php
    }
    else {
      header('Location: ./connexion.php');
      exit();
    }
  }
  ?>
