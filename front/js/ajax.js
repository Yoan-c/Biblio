function reserve(){
    let dataDispo = document.getElementById("idDispo").textContent;
    let dataIsbn = document.getElementById("idISBN").textContent;

    let nb = parseInt(dataDispo.split(":")[1].trim());
    let isbn = parseInt(dataIsbn.split(":")[1].trim());
    console.log(isbn);
    if(isNaN(nb) || isNaN(isbn)){
        console.log("Afficher une erreur");
    }
    else if (nb <= 0){
        console.log("plus de stock disponible")
    }
    else {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "reservation", true);
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                // Response
                var response = this.responseText;
                console.log("reponse serveur" + response);
            }
        };
        xhttp.send("stock="+nb+"&isbn="+isbn);
    }

}

function Pro_book(isbn){
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "prolonger", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
            console.log("reponse serveur" + response);
        }
    };
    xhttp.send("isbn="+isbn);
}

function getSearch(){
    let titre = document.getElementById("search_title").value;
    let auteur = document.getElementById("search_auteur").value;
    let genre = document.getElementById("search_genre").value;
    let langue = document.getElementById("search_langue").value;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/json/searchBook", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // Response
            var response = this.responseText;
            console.log("reponse serveur" + response);
        }
    };
    let test = {"data" : "1"};
    xhttp.send("titre="+titre+"&auteur="+auteur+"&genre="+genre+"&langue="+langue);
}

