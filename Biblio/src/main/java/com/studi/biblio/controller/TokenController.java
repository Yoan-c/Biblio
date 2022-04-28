package com.studi.biblio.controller;

import com.studi.biblio.encode.Empreinte;
import com.studi.biblio.model.Token;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.TokenRepository;
import com.studi.biblio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class TokenController {
    private static Map<String, Token> tokens = new HashMap<>();
    @Autowired
    private TokenRepository tokenR;
    private String generateId() {
        return Empreinte.getSalt(64);
    }

    public String getToken(UserController usrC, UserRepository userR, String mail, String password){ //Génère un Token pour l'utilisateur (si le couple pseudo/mot de passe est valide) et renvoie l'identifiant de ce Token.
        if(!usrC.isCorrectPassword(userR, mail, password)){ //Si le couple pseudo/mot de passe n'est pas valide, on renvoie null
            return null;
        } else { //Sinon
            List<User> usrL = usrC.getUserByMail(userR, mail);
            Token t = new Token((User) usrL.get(0)); //On crée le token
            String id = generateId(); //On génère un identifiant qui va permettre de retrouver le Token
            //tokens.put(Empreinte.getHash(id), t); //On stock le token dans un endroit accessible depuis le hash de l'identifiant (et surtout pas directement depuis l'identifiant)
            String token = Empreinte.getHash(id);
            List<Token> tokenUser = tokenR.getTokenByMail(mail);
            if(tokenUser.size() > 0){
                tokenR.updateToken(usrL.get(0).getEmail(), id, t, token);
            }else {
                tokenR.setToken(usrL.get(0).getEmail(), id, t, token);
            }
            return id; //On renvoie l'identifiant
        }
    }

    public boolean isValidToken(String id) { //Renvoie vrai si le Token aossicé à l'identifiant est valide, faux sinon
        /*
        if (id != null) {
            String hash = Empreinte.getHash(id);
            if (!tokens.containsKey(hash)) {
                return false;
            }
            return tokens.get(hash).isValid();
        }
        return false;
         */
        if (id != null){
            List<Token> token = tokenR.getTokenByIdToken(id);
            if(token != null){
                String hash = Empreinte.getHash(id);

                if(!token.get(0).getToken().equals(hash)){
                    return false;
                }
                return token.get(0).isValid();
            }
        }
        return false;
    }
}
