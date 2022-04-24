package com.studi.biblio.controller;

import com.studi.biblio.model.User;
import com.studi.biblio.repository.UserRepository;
import com.studi.biblio.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private UserRepository user;

    @GetMapping("/")
    public String index(String name, Model model) {
        return "home";
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
        System.out.println("REQUET " + req);
        if(session != null && session.getAttribute("USER_SESSION") != null)
            return "pret";
        else {
            req.getSession(false);
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
    public String book_list(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null)
            return "livres";
        else
            return "redirect:errconnexion";
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
}
