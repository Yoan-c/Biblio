package com.studi.biblio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studi.biblio.model.Genre;
import com.studi.biblio.model.Langue;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(value = "json")
public class MainJsonController {

    @Autowired
    private UserRepository user;
    @Autowired
    private LivreRepository livre;
    @Autowired
    private EditeurRepository editor;
    @Autowired
    private AuteurRepository author;
    @Autowired
    private PretRepository pretR;
    @Autowired
    private GenreRepository genre;
    @Autowired
    private LangueRepository langue;

    @Autowired
    private UserController usrC;
    @Autowired
    private TokenController tokenC;
    private ObjectMapper mapper = new ObjectMapper();


    @PostMapping("askConnexion")
    public String askConnexion(@RequestParam Map<String, String> userAccount, HttpServletRequest request) throws JsonProcessingException {
        User us =  user.getUserByMail(userAccount);
        String token = tokenC.getToken(usrC, user,userAccount.get("mail"), userAccount.get("password"));
        Map<String, String> dataResp = new HashMap<>();
        dataResp.put("token", token);
        if(token != null)
            dataResp.put("isConnect", "true");
        else
            dataResp.put("isConnect", "false");
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }
    @PostMapping("/home")
    public String home(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token")))
        {
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
            String booksInfo = livre.getAllinfoBook2(null);
            List<Genre> g = genre.getAll();
            List<Langue> l = langue.getAll();
            String getLangue = mapper.writeValueAsString(l);
            String getGenre = mapper.writeValueAsString(g);
            dataResp.put("langue", getLangue);
            dataResp.put("genre", getGenre);
            dataResp.put("book", booksInfo);
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }
    @PostMapping("/searchBook")
    public String book_Search(@RequestParam Map<String, String> info, HttpServletRequest request, Model model) throws IOException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token")))
        {
            String books = livre.searchBookInfo(info.get("titre"), info.get("auteur"), info.get("genre"), info.get("langue"));
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
            dataResp.put("book", books);
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }

    @PostMapping("/lend")
    public String lend_book(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();

        if(info.get("token") != null && tokenC.isValidToken(info.get("token"))) {
            List<User> us = user.getUserByMail(info.get("mail"));
            List<Object> lstObj = pretR.getPretAllInfo(String.valueOf(us.get(0).getId()));
            String lstLend = mapper.writeValueAsString(lstObj);
            dataResp.put("book", lstLend);
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;

    }
    @PostMapping("/prolonger")
    public String ajax_prolonger(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token"))) {
            List<User> us = user.getUserByMail(info.get("mail"));
            int res = pretR.setProlongation(info.get("isbn"),String.valueOf(us.get(0).getId()), true);
            String resultat = mapper.writeValueAsString(res);
            dataResp.put("res", resultat);
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }
    @PostMapping("/info")
    public String info_account(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token"))) {
            List<User>usr = user.getUserByMail(info.get("mail"));
            String usrString = mapper.writeValueAsString(usr);
            dataResp.put("user", usrString);
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }

    @PostMapping("/update")
    public String update_account(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token"))) {
            List<User> usr = user.getUserByMail(info.get("mail"));
            String usrString = mapper.writeValueAsString(usr);
            dataResp.put("user", usrString);
            dataResp.put("token", info.get("token"));
            dataResp.put("isConnect", "true");
        }
        else {
            dataResp.put("token", "");
            dataResp.put("isConnect", "false");
        }
        String jsonString = mapper.writeValueAsString(dataResp);
        return jsonString;
    }

    @PostMapping("/updateCompte")
    public String updateCompte(@RequestParam Map<String, String> info) throws JsonProcessingException {
        Map<String, String> dataResp = new HashMap<>();
        if(info.get("token") != null && tokenC.isValidToken(info.get("token"))) {
            List<User> usr = user.getUserByMail(info.get("mail"));
            if (!usr.isEmpty()){
                if (!info.get("mail").equals(info.get("mailModif"))){
                    dataResp.put("token", info.get("token"));
                    dataResp.put("isConnect", "true");
                    String jsonString = mapper.writeValueAsString(dataResp);
                    return jsonString;
                }
            }
            int success = user.updateUser(info, String.valueOf(usr.get(0).getId()));
            List<User> lstUser = user.getUserByMail(info.get("mail"));
            if(lstUser != null) {
                lstUser.get(0).setPassword("");
                lstUser.get(0).setSel("");
                String usrString = mapper.writeValueAsString(lstUser);
                dataResp.put("user", usrString);
                dataResp.put("token", info.get("token"));
                dataResp.put("isConnect", "true");
            }
        }
        else {
                dataResp.put("token", "");
                dataResp.put("isConnect", "false");
            }

            String jsonString = mapper.writeValueAsString(dataResp);
            return jsonString;
    }
    
    @PostMapping("/addUser")
    public String addUser(@RequestParam Map<String, String> infoUser) {
        boolean is_creat = false;

        is_creat = user.createUser(infoUser);
        if (!is_creat) {
            return "false";
        }
        else {
            String value = "true";
            return value;
        }
    }
}
