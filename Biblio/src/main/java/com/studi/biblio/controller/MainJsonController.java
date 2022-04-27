package com.studi.biblio.controller;

import com.studi.biblio.model.Livre;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.*;
import com.studi.biblio.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.logging.Logger;


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

    @RequestMapping("/json")
    public String index(String name, Model model) {
        return "{home:test}";
    }

    @GetMapping("/connexion")
    public String connexion() {
        return "connexion";
    }
    @PostMapping("askConnexion")
    public String askConnexion(@RequestParam Map<String, String> userAccount, Model model, HttpServletRequest request){
        User us =  user.getUserByMail(userAccount);
        Session session = new Session();
        boolean is_session = session.creatSession(us, request);
        if (is_session)
            return "redirect:lend";
        else
            return "redirect:connexion";

    }

    @GetMapping("/create_account")
    public String new_compte() {
        return "creationCompte";
    }

    @PostMapping("/create")
    public String creat_compte(@RequestParam Map<String, String> infoUser, Model model) {
        boolean is_creat = false;
        is_creat = user.createUser(infoUser);
        model.addAttribute("is_creat", is_creat);
        if (!is_creat) {
            return "creationCompte";
        }
        else {
            String value = "Connectez vous avec vos identifiant";
            model.addAttribute("message_co", value);
            return "redirect:connexion";
        }
    }

    @GetMapping("/lend")
    public String lend_book(String name, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String idUser = (String) session.getAttribute("USER_SESSION");
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
    public String book_Search(@RequestParam Map<String, String> info, HttpServletRequest request, Model model) {
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
