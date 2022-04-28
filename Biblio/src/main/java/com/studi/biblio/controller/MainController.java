package com.studi.biblio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studi.biblio.model.*;
import com.studi.biblio.repository.*;
import com.studi.biblio.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping(value = {"jsp"})
public class MainController {


    private UserRepository user;
    @Autowired
    private LivreRepository livre;
    @Autowired
    private EditeurRepository editor;
    @Autowired
    private AuteurRepository author;
    @Autowired
    private GenreRepository genre;

    @Autowired
    private PretRepository pretR;
    @Autowired
    private LangueRepository langue;


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
    public String update_account(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            List<User> lstUser = user.getUserByMail(session.getAttribute("mail").toString());
            if(lstUser != null){
                lstUser.get(0).setPassword("");
                lstUser.get(0).setSel("");
            }
            model.addAttribute("infoCompte", lstUser);
            return "modifierCompte";
        }
        else
            return "redirect:connexion";
    }
    @PostMapping("/updateCompte")
    public String updateCompte(@RequestParam Map<String, String> infoUser,HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String idUser = (String) session.getAttribute("USER_SESSION");
        if(session != null && idUser != null) {
            int success = user.updateUser(infoUser, idUser);
            if(success == 1)
                session.setAttribute("mail", infoUser.get("mail").toString());
            List<User> lstUser = user.getUserByMail(session.getAttribute("mail").toString());
            if(lstUser != null){
                lstUser.get(0).setPassword("");
                lstUser.get(0).setSel("");
            }
            model.addAttribute("infoCompte", lstUser);
            model.addAttribute("success", success);
            return "modifierCompte";
        }
        else
            return "redirect:connexion";
    }

    @GetMapping("/info")
    public String info_account(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            List<User> lstUser = user.getUserByMail(session.getAttribute("mail").toString());
            if(lstUser != null){
                lstUser.get(0).setPassword("");
                lstUser.get(0).setSel("");
            }
            model.addAttribute("infoCompte", lstUser);
            return "infoCompte";
        }else
            return "redirect:connexion";
    }

    @GetMapping("/search")
    public String book_list(HttpServletRequest request, Model model) throws JsonProcessingException {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            List<Genre> g = genre.getAll();
           // List<Object> booksInfo = livre.getAllinfoBook();
            String booksInfo = livre.getAllinfoBook2();
            List<Langue> l = langue.getAll();
            model.addAttribute("langue", l);
            model.addAttribute("genre", g);
            model.addAttribute("langueSize", l.size());
            model.addAttribute("genreSize", g.size());

            model.addAttribute("booksInfo", booksInfo);
            model.addAttribute("booksSize", booksInfo.length());

            return "livres2";
        }
        else
            return "redirect:errconnexion";
    }
    @PostMapping("/reservation")
    public String ajax_connexion(@RequestParam Map<String, String> info, HttpServletRequest request,  Model model) {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute("USER_SESSION") != null) {
            int state = user.reserveBook(request, info,  session);
            return "";
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
            return "redirect:lend";
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
}
