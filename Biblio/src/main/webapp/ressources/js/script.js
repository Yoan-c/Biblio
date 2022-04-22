window.onload = ()=>{
    /* animation menu */
    let nav = document.getElementById("nav").style;
    nav.display = "none";
    let menu = document.getElementById("menu");
    let nbclick = 0;
    menu.addEventListener('click', () => {
        nbclick++;
        nbclick = nbclick > 1 ? 2 : nbclick;

        console.log(nbclick);
        if (nbclick !== 1){
            setTimeout(()=>{nbclick = 0}, 1000);
            console.log("ioci");
        }
        else if(nbclick === 1) {
            setTimeout(()=>{nbclick = 0}, 2500);
            console.log("la");
                if (nav.display === "none") {
                    nav.animationName = "slidein";
                    nav.display = "block";

                } else {
                    nav.animationName = "slideout";
                    setTimeout(() => {
                        nav.display = "none";
                    }, 2800);
                }
        }
        nbclick = 1;
    });

    /* animation recherche */

    let logo_search = document.getElementById("logo_search");
    let form_search = document.getElementById("sect_search_champs");
    logo_search.addEventListener('click', () => {
        let is_slide = form_search.classList.contains("slide-in");
        let cl = "sect_search_champs";
        form_search.setAttribute('class', (is_slide) ? cl+" slide-out" : cl+" slide-in");
    })

    /* modal */
    let modal_book = document.getElementById("modal_book");
    let span = document.getElementsByClassName("close")[0];

}
/* modal */
let modal_book = document.getElementById("modal_book");
let span = document.getElementsByClassName("close")[0];
function showModal(e){

    modal_book.style.display= "block";
}
function hideModal(e){
    modal_book.style.display= "none";
}
