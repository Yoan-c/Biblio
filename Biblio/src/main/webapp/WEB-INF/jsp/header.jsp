<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="fr">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Home</title>
    <link rel="stylesheet" href="../../ressources/css/normalize.css" />
    <link rel="stylesheet" href="../../ressources/css/styles.css" />
    <link rel="stylesheet" href="../../ressources/css/styles-responsive.css" />
</head>
<body>
    <header>
        <div class="logo">
            <a href="<c:url value="/"/>"><img alt="logo" src="../../ressources/img/logo.png"></a>
        </div>
        <nav>
            <div id="menu" class="img_menu" >
                <img src="../../ressources/img/hamburger-menu-icon-transparent-21.jpg" alt="logo" width="45px"/>
            </div>
            <div class="top_menu" id="top_menu">
                <div class="left_menu">
                    <div class="left_m">
                        <div class="btn-black btn_plivre">
                            <a class="active " href="#">Parcourir les livres</a>
                        </div>
                        <div class="btn-black btn_pcours">
                            <a href="lend">Prêt en cours</a>
                        </div>
                    </div>
                </div>
                <div class="right_menu">
                    <div class="right_m">
                        <div class="btn-black btn-cmpt">
                            <a href="create_account">Créer un compte</a>
                        </div>
                        <div class="btn-black btn-co">
                            <a href="connexion">Se connecter</a>
                        </div>
                    </div>
                </div>
            </div>



        </nav>
    </header>