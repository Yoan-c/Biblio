package com.studi.biblio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studi.biblio.model.*;
import com.studi.biblio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static java.sql.Types.VARCHAR;

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
                rs.getDate("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }
    public List<Livre> getBookByName(String name) {
        String req = "SELECT * FROM livre WHERE titre LIKE ";
        String n = "'"+name+"%'";
        req += n;
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getLong("id_editeur"),
                rs.getDate("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }
    public List<Livre> getBookByLangue(String langue) {
        String req = "SELECT * FROM livre WHERE langue LIKE ";
        String n = "'"+langue+"%'";
        req += n;
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getLong("id_editeur"),
                rs.getDate("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }
    public List<Livre> getBookByGenre(String genre) {
        String req = "SELECT * \n" +
                "FROM `livre_genre` lg, livre l \n" +
                "WHERE lg.isbn = l.isbn\n" +
                "AND nom_genre = ?";
        return new ArrayList<>(JdT.query(req, new Object[]{genre}, new int[]{VARCHAR}, (rs, rowNum) -> new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getLong("id_editeur"),
                rs.getDate("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }
    public List<Livre> getBookById(String id) {
        String req = "SELECT * \n" +
                "FROM  livre l \n" +
                "WHERE isbn = ?";
        return new ArrayList<>(JdT.query(req, new Object[]{id}, new int[]{VARCHAR}, (rs, rowNum) -> new Livre(
                rs.getString("isbn"),
                rs.getString("titre"),
                rs.getLong("id_editeur"),
                rs.getDate("date_publication"),
                rs.getString("description"),
                rs.getString("langue"),
                rs.getString("couverture")
        )));
    }

    public List<Object> getAllinfoBook(List<Livre> lstLivre) {
        List<Livre> bookList = new ArrayList<>();
        if (lstLivre == null)
            bookList = this.getAll();
        else
            bookList = lstLivre;
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
    public String getAllinfoBook2(List<Livre> lstLivre) throws JsonProcessingException {
        List<Object> listObj = getAllinfoBook(lstLivre);
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

    public String searchBookInfo(String titre, String auteur, String genre, String langue) throws IOException {
        List<Object> lstObj = new ArrayList<>();
        List<Livre> lstBook = getBookByName(titre);
        boolean ispassedLangue = false;
        boolean ispassedGenre = false;
        if(lstBook.isEmpty()){
            lstBook = getBookByLangue(langue);
        }
        if(!lstBook.isEmpty() && langue!=null && !langue.equals("")) {
            ispassedLangue = true;
            lstBook.removeIf( name  -> (!name.getLangue().equals(langue)));
        }
        if(!genre.equals("")){
            if(!lstBook.isEmpty()){
                List<LivreGenre> livreGenre = lGenre.getByName(genre);
                List<Livre> newLstBook = new ArrayList<>();
                for(int i = 0 ; i < livreGenre.size(); i++){
                    String getGenre = livreGenre.get(i).getIsbn();
                    int index = -1;
                    for(int j = 0 ; j < lstBook.size(); j ++){
                        if(lstBook.get(j).getIsbn().equals(getGenre))
                            newLstBook.add(lstBook.get(j));
                    }
                }
                lstBook = newLstBook;
            }
            else if (lstBook.isEmpty() && ispassedLangue == false){
                lstBook = getBookByGenre(genre);
            }
        }
        if (!auteur.equals("")){

            List<LivreAuteur> lstLA = livreAR.getAllIsbnByAuthorName(auteur);

            if(!lstBook.isEmpty()){
                List<Livre> newLst = new ArrayList<>();
                for(int i = 0 ; i < lstLA.size(); i++){
                    for(int j = 0 ; j < lstBook.size(); j++){
                        String isbn = lstLA.get(i).getIsbn();
                        if (lstBook.get(j).getIsbn().equals(isbn)){
                            newLst.add(lstBook.get(j));
                        }
                    }
                }
                lstBook = newLst;
            }
            else if (lstBook.isEmpty() && ispassedLangue == false && ispassedGenre == false) {
                for(int i = 0 ; i < lstLA.size(); i++){
                    lstBook.add((Livre) getBookById(lstLA.get(i).getIsbn()));
                }

            }
        }
        return getAllinfoBook2(lstBook);
    }
}

