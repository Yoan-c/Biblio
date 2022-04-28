package com.studi.biblio.controller;

import com.studi.biblio.encode.Empreinte;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.sql.Types.VARCHAR;

@Controller
public class UserController {
    @Autowired
    private static JdbcTemplate JdT;


    /*public boolean isCorrectPassword(String mail, String password){
        Logger log = Logger.getLogger("");
        log.info("isCorrectPassword  : ");
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("mail", mail);
        mapUser.put("password", password);
        User user = userR.getUserByMail(mapUser);
        if(user != null){
            return true;
        }
        return false;
    }*/
    public static List<User> getUserByMail(UserRepository userR, String mail){ //Renvoie un User si il est présent dans la Map
        return userR.getUserByMail(mail);
    }
    public void addUserToMap(UserRepository userR, String mail, String password){ //Crée un utilisateur et l'ajoute à la Map
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "first");
        user.put("lastName", "Last");
        user.put("mail", mail);
        String salt = Empreinte.getSalt(56);
        String hashSalt = Empreinte.getHashSalt(password, salt);
        userR.createUser(user);
    }
    public User addUserToMapGetClient(UserRepository userR, String mail, String password){ //Crée un utilisateur et l'ajoute à la Map
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "first");
        user.put("lastName", "Last");
        user.put("mail", mail);
        String salt = Empreinte.getSalt(56);
        String hashSalt = Empreinte.getHashSalt(password, salt);
        user.put("salt", salt);
        user.put("password", hashSalt);
        userR.createUser(user);
        User usr = new User(user.get("mail"), user.get("password"), user.get("salt"));
        return usr;
    }

    public boolean isCorrectPassword(UserRepository userR, String mail, String password){ //renvoie vrai si le couple utilisateur/mot de passe est correct

        List<User> userList = userR.getUserByMail(mail);
        if(userList == null){
            return false; //L'utilisateur n'existe pas
        }
        User user = userList.get(0);
        return Empreinte.isValidPassword(user.getPassword(), password, user.getSel());
    }
}
