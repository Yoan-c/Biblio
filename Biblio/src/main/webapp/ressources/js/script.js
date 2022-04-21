window.onload = ()=>{
    let nav = document.getElementById("nav").style;
    nav.display = "none";
    let menu = document.getElementById("menu");
    let nbclick = 0;
    menu.addEventListener('click', () => {
        nbclick++;
        nbclick = nbclick > 1 ? 2 : nbclick;

        console.log(nbclick);
        if (nbclick != 1){
            setTimeout(()=>{nbclick = 0}, 1000);
            console.log("ioci");
        }
        else if(nbclick == 1) {
            setTimeout(()=>{nbclick = 0}, 2500);
            console.log("la");
                if (nav.display == "none") {
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
}

