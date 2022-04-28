package com.studi.biblio.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studi.biblio.model.*;
import com.studi.biblio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class LivreService implements LivreRepository {

    private static Logger logger = Logger.getLogger("");
    @Autowired
    private JdbcTemplate JdT;
    @Autowired
    private AuteurRepository auteur;
    @Autowired
    private GenreRepository genre;
    @Autowired
    private EditeurRepository editeur;
    @Autowired
    private LivreGenreRepository lGenre;
    @Autowired
    private LivreAuteurRepository livreAR;
    @Autowired
    private ExemplaireRepository livreEx;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<Livre> getAll() {
        String req = "SELECT * FROM livre";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getLong("id_editeur"),
                rs.getTimestamp("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }

    public List<Object> getAllinfoBook() {
        List<Livre> bookList = this.getAll();
        List<Editeur> editor = editeur.getAll();
        List<Auteur> aut = auteur.getAll();
        List<LivreAuteur> livreAuteur = livreAR.getAll();
        List<Genre> gender = genre.getAll();
        List<LivreGenre> LGenre = lGenre.getAll();
        List<Exemplaire> Lex = livreEx.getAll();
        List<Object> listObj = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++){
            List<Object> data = new ArrayList<Object>();
            String editeur = searchDataEditeur(bookList.get(i).getEditor(), editor);
            List<Auteur> lA = searchDataAuteur(bookList.get(i).getIsbn(), livreAuteur, aut);
            List<Genre> LG = searchDataGenre(bookList.get(i).getIsbn(), LGenre, gender);
            int nb = searchDataExemplaireDispo(bookList.get(i).getIsbn(), Lex);
            data.add(bookList.get(i));
            data.add(lA);
            data.add(LG);
            data.add(nb);
            data.add(editeur);
            listObj.add(data);
        }
        return listObj;
    }
    public String getAllinfoBook2() throws JsonProcessingException {
        List<Livre> bookList = this.getAll();
        List<Editeur> editor = editeur.getAll();
        List<Auteur> aut = auteur.getAll();
        List<LivreAuteur> livreAuteur = livreAR.getAll();
        List<Genre> gender = genre.getAll();
        List<LivreGenre> LGenre = lGenre.getAll();
        List<Exemplaire> Lex = livreEx.getAll();
        List<Object> listObj = new ArrayList<>();
        String book = "";
        for (int i = 0; i < bookList.size(); i++){
            List<Object> data = new ArrayList<>();
            String editeur = searchDataEditeur(bookList.get(i).getEditor(), editor);
            List<Auteur> lA = searchDataAuteur(bookList.get(i).getIsbn(), livreAuteur, aut);
            List<Genre> LG = searchDataGenre(bookList.get(i).getIsbn(), LGenre, gender);
            int nb = searchDataExemplaireDispo(bookList.get(i).getIsbn(), Lex);
            data.add(bookList.get(i));
            data.add(lA);
            data.add(LG);
            data.add(nb);
            data.add(editeur);
            listObj.add(data);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(listObj);
    }
    public int searchDataExemplaire(String isbn, List<Exemplaire> exemplaire){
        int nb = 0;
        for(int i = 0 ; i < exemplaire.size(); i++){
            if (isbn.equals(exemplaire.get(i).getIsbn()))
                nb++;
        }
        return nb;
    }
    public int searchDataExemplaireDispo(String isbn, List<Exemplaire> exemplaire){
        int nb = 0;
        for(int i = 0 ; i < exemplaire.size(); i++){
            if (isbn.equals(exemplaire.get(i).getIsbn()) && exemplaire.get(i).getPret() == false)
                nb++;
        }
        return nb;
    }
    public List<Genre> searchDataGenre(String isbn, List<LivreGenre> Lg, List<Genre> genre)
    {
        List<Genre> genreLst = new ArrayList<>();
        for (int i = 0; i < Lg.size(); i++){
            if (Lg.get(i).getIsbn().equals(isbn)){
                for (int j = 0; j < genre.size(); j++){
                    if (genre.get(j).getGenre().equals(Lg.get(i).getNomGenre())){
                        genreLst.add(genre.get(j));
                    }
                }
            }
        }
        return genreLst;
    }

    public String searchDataEditeur(long id, List<Editeur> e){
        for (int i = 0 ; i < e.size(); i++){
            if (id == e.get(i).getId()){
                return e.get(i).getName();
            }
        }
        return null;
    }
    public List<Auteur> searchDataAuteur(String isbn, List<LivreAuteur> LAuteur, List<Auteur> aut)
    {
        List<Auteur> auteurLst = new ArrayList<>();

        for (int i = 0; i < LAuteur.size(); i++){
            if (LAuteur.get(i).getIsbn().equals(isbn)){
                for (int j = 0; j < aut.size(); j++){
                     if (aut.get(j).getId() == LAuteur.get(i).getId_auteur()){
                         auteurLst.add(aut.get(j));
                     }
                }
            }
        }
        return auteurLst;
    }

    public List<Editeur> getAllEditeur(){
        List<Editeur> ed = editeur.getAll();
        if(ed != null)
            return ed;
        return null;
    }
    public List<Auteur> getAllAuteur(){
        List<Auteur> aut = auteur.getAll();
        if (aut != null)
            return aut;
        return null;
    }

    public void verifyReservation(String stock, String isbn, String idUser, String mail) {
        if (isbn != null) {
            List<Exemplaire> ex = livreEx.getExemplaireById(isbn);
            System.out.println(ex);

            if( ex != null && !ex.isEmpty()){
                //livreEx.
            }
        }
    }

    public void searchBookInfo(String titre, String auteur, String genre, String langue) throws IOException {
        List<Object> lstObj = getAllinfoBook();
        logger.info("searchBookInfo : "+ lstObj+" "+ lstObj.size());
        List<Object> lstBook = new ArrayList<>();
        test(lstObj);
        /*if(titre != null && !titre.equals("")){
            searchInfoBookTitle(lstObj, lstBook, titre);
        }
        if(auteur != null && !auteur.equals("")){
            searchInfoBookAuteur(lstObj, lstBook, auteur);
        }
        if(genre != null && !genre.equals("")){
            searchInfoBookGenre(lstObj, lstBook, genre);
        }
        if(langue != null && !genre.equals("")){
            searchInfoBookLangue(lstObj, lstBook, langue);
        }*/
    }
    public List<Object> searchInfoBookTitle(List<Map<String, Object>> lstObj, List<Object> lstBook, String titre){
        if(lstObj != null){
                logger.info("test e "+lstObj.get(0).get("book"));


            for (int i = 0; i < lstObj.size(); i++){
                Object title =  lstObj.toArray();

                if (lstObj.get(i).toString().substring(0, titre.length()).indexOf(titre) >= 0){
                    lstBook.add(lstObj.get(i));
                }
            }
        }
        return lstBook;
    }
    public List<Object> searchInfoBookAuteur(List<Map<String, Object>> lstObj, List<Object> lstBook, String titre){
        logger.info("searchinfoBookAuteur "+lstBook);
        return null;
    }
    public List<Object> searchInfoBookGenre(List<Map<String, Object>> lstObj, List<Object> lstBook, String titre){
        logger.info("searchinfoBookGenre "+lstBook);
        return null;
    }
    public List<Object> searchInfoBookLangue(List<Map<String, Object>> lstObj, List<Object> lstBook, String titre){
        logger.info("searchinfoBookLangue "+lstBook);
        return null;
    }

    public void test(List<Object> ls) throws IOException {
        // Creating object of Organisation

        ObjectMapper mapper = new ObjectMapper();
        List<Object> lst = getAllinfoBook();

        //List<Object> t = new ArrayList<>();
        //mapper.writeValue ((JsonGenerator) t, lst);
        String t = new ObjectMapper().writeValueAsString(lst);
        logger.info("test "+ t);
        // Insert the data into the object
    }

}

