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



}
/* modal */
let modal_book = document.getElementById("modal_book");
function showModal(){

    modal_book.style.display= "block";
}
function hideModal(){
    modal_book.style.display= "none";
}
