
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
        <a href="./home.php"><img alt="logo" src="img/logo.png" width="200px"></a>
    </div>
    <nav>
        <div id="menu" class="img_menu" >
            <img src="img/hamburger-menu-icon-transparent-21.jpg" alt="logo" width="45px"/>
        </div>
        <div class="top_menu" id="top_menu">
            <div class="left_menu">
            <div class="left_m">
                <div class="btn-black btn_plivre">
                    <a id="idAllBook" href="./book.php" <?PHP if (strpos($_SERVER["SCRIPT_NAME"], "book")) echo "class='active'"?>>Parcourir les livres</a>
                </div>
                <div class="btn-black btn_pcours">
                    <a id="idLendBook" href="./lend.php" <?PHP if (strpos($_SERVER["SCRIPT_NAME"], "lend")) echo "class='active'"?>>Prêt en cours</a>
                </div>
            </div>
        </div>
            <div class="right_menu">
            <div class="right_m">
                <div class="btn-black btn-cmpt">
                    <a id="InfoBook" href="./info.php" <?PHP if (strpos($_SERVER["SCRIPT_NAME"], "info")|| strpos($_SERVER["SCRIPT_NAME"], "Compte")) echo "class='active'"?>>Information du compte</a>
                </div>
                <div class="btn-black btn-co">
                    <a href="./deconnexion.php">Se déconnecter</a>
                </div>
            </div>
        </div>
        </div>
    </nav>
</header>
