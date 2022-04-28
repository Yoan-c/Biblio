<?php
  $PATH = "http://localhost:8080/json/";
?>
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home</title>
    <link rel="stylesheet" href="css/normalize.css" />
    <link rel="stylesheet" href="css/styles.css" />
    <link rel="stylesheet" href="css/styles-responsive.css" />
</head>
<body>
    <header>
        <div class="logo">
            <a href="/"><img alt="logo" src="img/logo.png"></a>
        </div>
        <nav>
            <div id="menu" class="img_menu" >
                <img src="img/hamburger-menu-icon-transparent-21.jpg" alt="logo" width="45px"/>
            </div>
            <div class="top_menu" id="top_menu">
                <div class="left_menu">
                    <div class="left_m">
                        <div class="btn-black btn_plivre">
                            <a class="active " href="<?=$PATH?>/search">Parcourir les livres</a>
                        </div>
                    </div>
                </div>
                <div class="right_menu">
                    <div class="right_m">
                        <div class="btn-black btn-cmpt">
                            <a href="./create_account.php">Créer un compte</a>
                        </div>
                        <div class="btn-black btn-co">
                            <a href="./connexion.php">Se connecter</a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </header>
