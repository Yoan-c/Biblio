window.onload = ()=>{
    /* animation menu */
    let nav = document.getElementById("top_menu");
    let menu = document.getElementById("menu");
    let nbclick = 0;
    menu.addEventListener('click', () => {
        let is_slide = nav.classList.contains("slidein");
        let cl = "top_menu";
        nav.setAttribute('class', (is_slide) ? cl+" slideout" : cl+" slidein");

    });

    /* animation recherche */

    let logo_search = document.getElementById("logo_search");
    let form_search = document.getElementById("sect_search_champs");
    if (logo_search)
        logo_search.addEventListener('click', () => {
            let is_slide = form_search.classList.contains("slide-in");
            let cl = "sect_search_champs";
            form_search.setAttribute('class', (is_slide) ? cl+" slide-out" : cl+" slide-in");
        })

    // a tester
    function setmessageErrorModif(message){
        let errorModif = document.getElementById("errorModif");
        let p = document.getElementById("errorModifMsg");
        if (p == null){
            p = document.createElement("p");
            p.id = "errorModifMsg";
        }
        p.textContent = message;
        errorModif.appendChild(p);
    }
    function setmessageErrorReset(id){
        let errorModif = document.getElementById(id);
        if (errorModif){
            errorModif.textContent = "";
        }
    }
    function ValidateEmail(mail)
    {
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
        {
            return (true)
        }
        return (false)
    }

    let btnForm = document.getElementById("btnValidModif");
    if(btnForm){
        btnForm.addEventListener('click', (e) => {
            e.preventDefault();
            setmessageErrorReset("errorModifMsg");
            setmessageErrorReset("resModifSuccess");
            setmessageErrorReset("resModifError");
            let form = document.getElementById("formModif");
            let lastName = document.getElementById("lastNameModif").value;
            let firstName = document.getElementById("firstNameModif").value;
            let mailModif = document.getElementById("mailModif").value;
            let mdpModif = document.getElementById("mdpModif").value;
            let mdpConfirm = document.getElementById("mdpConfirmModif").value;
            console.log(lastName, " ", firstName, "", mailModif)
            if (lastName == "" || firstName == "" || mailModif == ""){
                setmessageErrorModif("Veuillez vérifier les champs");
                return;
            }
            else if(!ValidateEmail(mailModif)){
                setmessageErrorModif("Veuillez vérifier votre adresse mail");
                return;
            }
            else if(mdpModif !== mdpConfirm || mdpConfirm == "" || mdpModif == "") {
                setmessageErrorModif("Les mots de passe doivent être identique");
                return;
            }
            else
                form.submit();
        })
    }

}

function parserAuteur(data){
    const regex = /Auteur/m;
    let d = data.split("Auteur");
    const regexD = /\(/m;
    const regexF = /\)/m;
    let auteur = new Object();
    for(let i = 1; i < d.length; i++){
        let format = d[i].substring(d[i].search((regexD))+1, d[i].search(regexF));
        format = format.split(',');
        let first_name = format[1].split('=')[1];
        let last_name = format[2].split('=')[1];
        auteur[i-1] =
            {
                'firstName' : first_name,
                'lastName' : last_name
            };
    }
    return auteur;
}

function parserGenre(data){
    let dataG = data.substring(1, data.length-2);

    dataG = dataG.split(', ');
    let genre = new Object();
    for (let i = 0; i < dataG.length; i++){
        let g = dataG[i].split('genre=');
        if (g[1][g[1].length-1]==")"){
            genre[i] = g[1].substring(0, g[1].length-1);
        }
        else
            genre[i] = g[1];
    }
    return genre;
}

function parserdata(data, dataAuteur, dataGenre, exemplaire, editeur){
    let book = new Object();
    let dataBook = data.substring(0, data.length-1);
    book.book = parserBook(dataBook);
    book.auteur = parserAuteur(dataAuteur);
    book.genre = parserGenre(dataGenre);
    book.editeur = editeur;
    book.exemplaire = exemplaire;
    return book;
}
function parserBook(d){
    d = JSON.stringify(d);
    let data = d;
    let newData = data.substring(6, data.length-1);
    const regex_desc = /description=/g;
    let newData_before = data.substring(7, data.search(regex_desc) - 2);
    const regex_l = /langue=/g;
    let newData_after = data.substring(data.search(regex_desc), data.search(regex_l) - 2);
    let newData_end = data.substring(data.search(regex_l), data.length-1);
    newData = newData_before.split(',');
    let book = new Object();
    for (let i = 0; i < newData.length; i++) {
        let info = newData[i].split('=');
        book[info[0].trim()] = info[1].trim();
    }
    newData_end = newData_end.split(',');
    for (let i = 0; i < newData_end.length; i++) {
        let info = newData_end[i].split('=');
        book[info[0].trim()] = info[1].trim();
    }
    let info = newData_after.split('=');
    book[info[0].trim()] = info[1].trim();
    return book;
}
/*
    remplissage page
 */
    let selectGenre = document.getElementById("search_genre");
/* modal */
let modal_book = document.getElementById("modal_book");
function showModal(dataBook, dataAuteur, dataGenre, exemplaire, editeur){
    console.log("data "+JSON.parse(dataBook))
    let book = parserdata(dataBook, dataAuteur, dataGenre, exemplaire, editeur);
    let left_modal = document.getElementById("left_modal");
    let getImg = document.getElementById("add_img_modal");
    let right_modal = document.getElementById("right_modal");
    let firstP_modal = document.getElementById("idTitre");
    let descModal = document.getElementById("idDescModal");
    let reserve = document.getElementById("idBtnReserver")
    let divBtnModal = document.getElementById("idBtnModal");
    if(getImg == null){
        let img = document.createElement('img');

        img.src = book.book.cover;
        img.alt = "image de couverture";
        img.id = "add_img_modal";
        left_modal.appendChild(img);
    }
    else {
        getImg.src = book.book.cover;
    }

    if (firstP_modal == null )
    {

        getFormatP("Titre", book.book.title, right_modal);
        getFormatP("Auteur", book.auteur, right_modal);
        getFormatP("Editeur", book.editeur, right_modal);
        getFormatP("Genre", book.genre, right_modal);
        getFormatP("Langue", book.book.langue, right_modal);
        getFormatP("Date de publication", book.book.date_publication.split(" ")[0], right_modal);
        getFormatP("ISBN", book.book.isbn, right_modal);
        getFormatP("Disponibilité", book.exemplaire, right_modal);
        formatDesc("Présentation", book.book, descModal);
        formatBtn(book.exemplaire, divBtnModal);
    }
    else{
        getChangeInfoP("Titre", book.book.title, right_modal);
        getChangeInfoP("Auteur", book.auteur, right_modal);
        getChangeInfoP("Editeur", book.editeur, right_modal);
        getChangeInfoP("Genre", book.genre, right_modal);
        getChangeInfoP("Langue", book.book.langue, right_modal);
        getChangeInfoP("Date de publication", book.book.date_publication.split(" ")[0], right_modal);
        getChangeInfoP("ISBN", book.book.isbn, right_modal);
        getChangeInfoP("Disponibilité", book.exemplaire, right_modal);
        formatDescModify("Présentation", book.book, descModal);
        formatBtnModify(book.exemplaire, divBtnModal);
    }
    modal_book.style.display= "block";
}

function formatBtnModify(nb){
    let text="";
    let cl = "btn_reserver";
    let btn = document.getElementById("idBtnReserver");

    btn.classList.add(cl);
    if (nb > 0){
        text = "réserver";
        cl = "active_lend";
        btn.classList.remove("inactive_lend");
    }
    else{
        text = "indisponible";
        cl = "inactive_lend";
        btn.classList.remove("active_lend");
    }
    btn.classList.add(cl);
    btn.textContent = text;
    btn.onclick = reserve;
}
function formatBtn(nb, divBtnModal) {
    let text="";
    let btn = document.createElement("button");
    let cl = "btn_reserver";
    btn.classList.add(cl);
    if (nb > 0){
        text = "réserver";
        cl = "active_lend";
    }
    else{
        text = "indisponible";
        cl = "inactive_lend";
    }
    btn.textContent = text;
    btn.id = "idBtnReserver";
    btn.classList.add(cl);
    btn.onclick = reserve;
    divBtnModal.appendChild(btn);


}

 function formatAuteur(data){
    let d= "";
    for(key in data){
        d += data[key].firstName+" "+data[key].lastName+", ";
    }
    d = d.substring(0, d.length-2);
    return d;
 }

 function formatGenre(data){
    let d = "";
     for(key in data){
         d += data[key]+", ";
     }
     d = d.substring(0, d.length-2);
     return d;
 }

 function formatDesc(title, data, desc){
    let h3 = document.createElement('h3');
    h3.textContent = title+" : ";

    let centerM = document.getElementById("idCenterModal");
     let p = document.createElement('p');
     p.id = "idDesc";
     p.textContent = data.description
     desc.appendChild(h3);
     desc.appendChild(p);
 }

 function formatDescModify(title, data, desc){
     let p = document.getElementById("idDesc");
     p.textContent = data.description

}
function getFormatP(title, data, r_modal){
    let p = document.createElement('p');

    if (title == 'Genre'){
        data = formatGenre(data);
    }
    if (title == 'Auteur'){
        data = formatAuteur(data);
    }
    p.textContent = title+" : "+data;
    if (title == "Disponibilité")
        p.id = "idDispo";
    else if(title == "Date de publication")
        p.id = "idDate";
    else
        p.id = "id"+title;
    r_modal.appendChild(p);
}
function getChangeInfoP(title, data, r_modal) {
    let p = document.getElementById("id" + title);
    if (title == 'Genre'){
        data = formatGenre(data);
    }
    if (title == 'Auteur'){
        data = formatAuteur(data);
    }

    if (title == "Disponibilité") {
        p = document.getElementById("idDispo");
    }
    if(title == "Date de publication") {
        p = document.getElementById("idDate");
    }
    p.textContent = title+" : "+data;

}
function hideModal(){
    modal_book.style.display= "none";
}


