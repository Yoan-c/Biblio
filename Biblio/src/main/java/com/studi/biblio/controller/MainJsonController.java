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
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping(value = "json")
public class MainJsonController {

    Logger logger = Logger.getLogger("");
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
            String booksInfo = livre.getAllinfoBook2();
            List<Genre> g = genre.getAll();
            List<Langue> l = langue.getAll();
           // String books = mapper.writeValueAsString(booksInfo);
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

    @GetMapping("/create_account")
    public String new_compte() {
        return "creationCompte";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam Map<String, String> infoUser) {
        boolean is_creat = false;
        is_creat = user.createUser(infoUser);
        if (!is_creat) {
            return "creationCompte";
        }
        else {
            String value = "Connectez vous avec vos identifiant";
            return value;
        }
    }

    @GetMapping("/lend")
    public String lend_book(String name, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String idUser = (String) session.getAttribute("USER_SESSION");
        logger.info("lend_book "+idUser);
        if(session != null && idUser != null) {

            List<Object> lstObj = pretR.getPretAllInfo(idUser);
            model.addAttribute("listPret", lstObj);
            model.addAttribute("sizeList", lstObj.size()-1);
            return "pret";
        }
        else {
            return "redirect:connexion";
        }
    }

    @GetMapping("/update")
    public String update_account(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null)
            return "modifierCompte";
        else
            return "redirect:connexion";
    }

    @GetMapping("/info")
    public String info_account(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null)
            return "infoCompte";
        else
            return "redirect:connexion";
    }


    @GetMapping("/search")
    public String book_list(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            List<Object> booksInfo = livre.getAllinfoBook();
            model.addAttribute("booksInfo", booksInfo);
            model.addAttribute("books", livre.getAll());
            model.addAttribute("booksSize", booksInfo.size());
            model.addAttribute("editor", editor.getAll());
            model.addAttribute("author", author.getAll());
            return "livres";
        }
        else
            return "redirect:errconnexion";
    }
    @PostMapping("/searchBook")
    public String book_Search(@RequestParam Map<String, String> info, HttpServletRequest request, Model model) throws IOException {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            Logger logger = Logger.getLogger("");
            logger.info("/json/searchBook " +info);
            livre.searchBookInfo(info.get("titre"), info.get("auteur"), info.get("genre"), info.get("langue"));
            return "ICI";
        }
        else
            return "redirect:errconnexion";
    }

    @PostMapping("/reservation")
    public String ajax_connexion(@RequestParam Map<String, String> info, HttpServletRequest request,  Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            int state = user.reserveBook(request, info,  session);
            return "ICI";
        }
        else{
            return "null";
        }
    }
    @PostMapping("/prolonger")
    public String ajax_prolonger(@RequestParam Map<String, String> info, HttpServletRequest request,  Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            pretR.setProlongation(info.get("isbn"),(String)session.getAttribute("USER_SESSION"), true);
            return "redirect:lend";
        }
        else{
            return "redirect:error";
        }
    }
    @GetMapping("/errconnexion")
    public String ask_connexion(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "demandeConnexion";
    }

    @GetMapping("/deconnexion")
    public String deconnexion(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("USER_SESSION");
        session.invalidate();
        session = null;
        return "redirect:connexion";
    }
    @GetMapping("/error")
    public String errorGet() {
            return "erreur";
    }
    @PostMapping("/error")
    public String errorPost() {
        return "erreur";
    }
}
