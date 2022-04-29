<?php
require_once("./function/function.php");
  session_start();
  if(!isset($_SESSION) || !isset($_SESSION['userToken']) || !isset($_SESSION['loggued_on_userMail']))
  {
    header('Location: ./connexion.php');
    exit();
  }
  else
  {
    $result = getLend();
      $result = json_decode($result, true, 6);
    if ($result && $result["isConnect"] === "true")
    {

    //    $result = json_decode($result, true, 6);
        $lstBook = json_decode($result["book"]);
    }
    if($result["isConnect"] === "true" && $_SESSION['userToken'] === $result["token"])
    {
?>
    <?php include'./header_co.php';?>
      <main class="main_book">
        <div class="main_section">
            <div class="book_lend">
              <?php
              $i = 0;
              for($i = 0; $i < sizeof($lstBook); $i++){
                $book = $lstBook[$i];
              ?>
            <section>
              <div class="img_book">
                <img src="<?php echo $book[0]->{"couverture"};?>" alt="image de couverture" width="120">
              </div>
              <div class="info_book">
                <div class="desc_book">
                  <p>Titre : <?php echo $book[0]->{"titre"};?></p>
                  <p>Auteur :
                  <?php
                    for($j = 1; $j <= $book[2][0]->{"nb"} ; $j++){
                      echo $book[2][$j]->{"firstName"}." ".$book[2][$j]->{"lastName"};
                      if($j < $book[2][0]->{"nb"})
                       echo", ";
                    }
                  ?>
                  </p>
                  <p> Genre :
                    <?php  for($j = 1; $j <= $book[3][0]->{"nb"}; $j++){
                      echo $book[3][$j]->{"nomGenre"};
                      if($j < $book[3][0]->{"nb"})
                       echo", ";
                      }
                      ?>
                  </p>
                  <p>Editeur : <?php echo $book[1][0];?></p>
                  <p>Date de publication : <?php echo date('d-m-Y',substr(strval($book[0]->{"date_publication"}), 0, -3))?></p>
                  <p>ISBN : <?php echo $book[0]->{"isbn"};?></p>
                  <p>Début du prêt :  <?php echo date('d-m-Y',substr(strval($book[0]->{"date_debut"}), 0, -3));?></p>
                  <p>Fin du prêt : <?php echo date('d-m-Y',substr(strval($book[0]->{"date_fin"}), 0, -3));?></p>
                </div>
                <div class="reserve_book">
                  <?php if($book[0]->{"renouvellement"} == false) {
                    ?>
                      <button class="btn_reserve_book active_lend" onclick="Pro_book(<?php echo $book[0]->{"isbn"};?>)">Prolonger la période de pret</button>
                      <input type="hidden" id="idToken" value="<?php echo$_SESSION['userToken'];?>"/>
                        <input type="hidden" id="idMail" value="<?php echo$_SESSION['loggued_on_userMail'];?>"/>
                    <?php
                  } else {?>
                  <button class="btn_reserve_book inactive_lend" >Impossible de prolonger la période de prêt une seconde fois</button>
                <?php }?>
                  </div>
              </div>
            </section>
            <?php  if (sizeof($lstBook) == 0) {?>
            <div>
              <p>
                aucun prêt en cours
              </p>
            </div>
          <?php }?>
          <?php
          }
          ?>
          </div>
        </div>
      </main>

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
