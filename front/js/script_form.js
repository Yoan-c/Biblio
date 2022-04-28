let PATH = "http://localhost:8080/json/";
window.onload = ()=>{
    /* form connexion */
    let btnConnect = document.getElementById("btnFormConnect");
    if(btnConnect){
      btnConnect.addEventListener('click', e => {

        let formConnect = document.getElementById("form_connect");
          e.preventDefault();
          console.log("ici");
          let mail = document.getElementById("mail").value;
          let password = document.getElementById("password").value;
          if(!mail || !password)
            console.log("erreur contenu")
          else {
          /*  var xhttp = new XMLHttpRequest();
            xhttp.open("POST", PATH+"askConnexion", true);
            xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    // Response
                    var response = this.response;
                    res = response.toString();
                    res = JSON.parse(res);
                    console.log("test "+res+" "+ res.token);
                    if(res && res.isConnect){
                      document.location.href = PATH+'home?token='+res.token

                    }else{
                      //erreur
                      return;
                    }
                }
            };
            xhttp.send("mail="+mail+"&password="+password);
          */
          formConnect.submit();
          }
        })
    }

}
