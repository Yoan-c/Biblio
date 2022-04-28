package com.studi.biblio.service;

import com.studi.biblio.model.*;
import com.studi.biblio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.Types.*;

@Service
public class PretService implements PretRepository {

    @Autowired
    private JdbcTemplate JdT;
    @Autowired
    private ExemplaireRepository ex;
    @Autowired
    private AuteurRepository auteur;
    @Autowired
    private LivreAuteurRepository LivreA;
    @Autowired
    private LivreGenreRepository LivreG;
    @Autowired
    private EditeurRepository editeur;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<Pret> getAll() {
        String req = "SELECT * FROM pret";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Pret(
                rs.getLong("id"),
                rs.getBoolean("renouvellement"),
                rs.getTimestamp("date_debut"),
                rs.getTimestamp("date_fin"),
                rs.getLong("id_utilisateur"),
                rs.getLong("id_exemplaire")
        )));
    }

    @Override
    public List<Map<String, Object>> getPretById(String id) {

        if(id != null && !id.equals("")){
                String requete = "SELECT u.nom AS nomUser, u.prenom as prenomUser , u.email, p.renouvellement, p.date_debut, p.date_fin, p.id_exemplaire, ex.isbn, titre, date_publication, couverture, id_editeur\n" +
                        "FROM utilisateur u, pret p, exemplaire ex, livre l\n" +
                        "WHERE u.id = p.id_utilisateur\n" +
                        "AND ex.id = p.id_exemplaire\n" +
                        "AND l.isbn = ex.isbn\n" +
                        "AND p.id_utilisateur= ?";
            List<Map<String, Object>> lst = (JdT.queryForList(requete,new Object[]{id}, new int[]{VARCHAR} ));

            return lst;
        }
        return null;
    }
    public List<Map<String, Object>> getPretIsbnId(String id, String isbn) {

        if(id != null && !id.equals("")){
            String requete = "SELECT p.id as pId, isbn, pret, ex.id as exId, renouvellement, date_debut, date_fin, id_utilisateur, id_exemplaire\n" +
                    "FROM  pret p, exemplaire ex\n" +
                    "WHERE ex.id = p.id_exemplaire\n" +
                    "AND ex.isbn = ?\n" +
                    "AND p.id_utilisateur= ?";
            List<Map<String, Object>> lst = (JdT.queryForList(requete,new Object[]{isbn, id}, new int[]{VARCHAR,VARCHAR} ));

            return lst;
        }
        return null;
    }

    public ArrayList<Object> getPretAllInfo(String id){
        List<Map<String, Object>> lstPret = getPretById(id);
        List<LivreAuteur> lstAuteurR = LivreA.getAll();
        List<Auteur> aut = auteur.getAll();
        List<LivreGenre> LGenre = LivreG.getAll();
        ArrayList<Object> listObj = new ArrayList<>();
        for (int i = 0; i < lstPret.size(); i++)
        {
            ArrayList<Object> data = new ArrayList<>();
            data.add(lstPret.get(i));
            data.add(editeur.getEditeurById((int)lstPret.get(i).get("id_editeur")));
            data.add(searchAuteur(lstPret.get(i), aut, lstAuteurR));
            data.add(searchGenre(lstPret.get(i), LGenre));
            listObj.add(data);
        }
        return listObj;
    }
    public ArrayList<Object> searchAuteur(Map<String, Object>lstPret, List<Auteur> aut, List<LivreAuteur> lstAuteurR){
        ArrayList<Object> idAuteur = new ArrayList<>();
        HashMap<String, Integer> nb = new HashMap<String, Integer>();
        int count = 0;
        nb.put("nb", count);
        idAuteur.add(nb);
        for(int i = 0 ; i < lstAuteurR.size(); i++){
            if (lstPret.get("isbn").equals(lstAuteurR.get(i).getIsbn())){
                for(int j = 0 ; j < aut.size(); j++){
                    if(lstAuteurR.get(i).getId_auteur() == aut.get(j).getId()){
                        idAuteur.add(aut.get(j));
                        count++;
                    }
                }
            }
        }
        nb.put("nb", count);
        return idAuteur;
    }

    public ArrayList<Object> searchGenre(Map<String, java.lang.Object>lstPret, List<LivreGenre> lstGenreR){
        ArrayList<Object> idGenre = new ArrayList<>();
        HashMap<String, Integer> nb = new HashMap<String, Integer>();
        int count = 0;
        nb.put("nb", count);
        idGenre.add(nb);
        for(int i = 0 ; i < lstGenreR.size(); i++){
            if (lstPret.get("isbn").equals(lstGenreR.get(i).getIsbn())){
                idGenre.add(lstGenreR.get(i));
                count++;
            }
        }
        nb.put("nb", count);
        return idGenre;
    }

    @Override
    public void addPretBook(String isbn,  List<Exemplaire> lst, String idUser, boolean re){
        boolean renouvellement = re;
        Date dateNow = new Date();
        Date dateAfter = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateNow);
        c.add(Calendar.DATE, 7);
        dateAfter = c.getTime();
        ex.setExemplaireDispoById(isbn, true, lst.get(0).getId());
        String req = "INSERT INTO pret (renouvellement, date_debut, date_fin, id_utilisateur, id_exemplaire)" +
                " VALUES (?,?,?,?,?)";
        JdT.update(req, renouvellement, dateNow, dateAfter, idUser, lst.get(0).getId());

    }
    public void setRenouvellementBook(List<Map<String, Object>> lst, int pos){
        Date dateNow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateNow);
        c.add(Calendar.DATE, 7);
        dateNow = c.getTime();
        String req = "UPDATE pret SET renouvellement=?,date_fin=? WHERE id_exemplaire =? ";
        JdT.update(req,true, dateNow, lst.get(pos).get("id_exemplaire"));
    }
    @Override
    public int checkEmpruntBook(List<Map<String, Object>> lstPret, String isbn, String stock, String idUser ){

        List<Exemplaire> lstEx = ex.getExemplaireDispoById(isbn, false);
        List<Exemplaire> Ex = ex.getExemplaireById(isbn);
        if (lstEx == null)
            return -1;
        if(Ex.isEmpty() || lstEx.isEmpty()) {
            // plus de livre
            return 0;
        }
        if(lstPret.isEmpty()){
                addPretBook(isbn, lstEx, idUser, false);
                return 1;
        }
        else {
            int pos = checkPretBook(lstPret, isbn);
            boolean renouvellement = false;

            if(pos >= 0) {
                renouvellement = checkRenouvellementBook(lstPret, pos);
                if(renouvellement) {
                    setRenouvellementBook(lstPret, pos);
                    return 1;
                }
                else
                    return 2;
            }
            else {
                addPretBook(isbn, lstEx, idUser, false);
                return 1;
            }
        }
    }

    public int checkPretBook( List<Map<String, Object>> lst, String isbn){
        int i = 0;
        int pos = -1;
        while (i < lst.size()){
            if(lst.get(i).get("isbn").equals(isbn)){
                pos = i;
                i += lst.size() +1;
            }
            i++;
        }
        return pos;
    }
    public boolean checkRenouvellementBook(List<Map<String, Object>> lst, int pos){
        boolean r = (boolean) lst.get(pos).get("renouvellement");
        if (r == false){
            return true;
        }
        return false;
    }

    public void setProloBook(String idPret, boolean b){
        Date dateNow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateNow);
        c.add(Calendar.DATE, 7);
        dateNow = c.getTime();
        String req = "UPDATE pret SET renouvellement=? ,date_fin= ?  WHERE id = ?";
        JdT.update(req,b, dateNow, idPret);
    }
    @Override
    public int setProlongation(String isbn, String idUser, boolean b){
        if(isbn == null || idUser == null)
            return -1;
        List<Map<String, Object>> lst = getPretIsbnId(idUser, isbn);
        if(lst != null && !lst.isEmpty()){
            if((boolean)lst.get(0).get("renouvellement") == false){
                setProloBook(lst.get(0).get("pId").toString(), b);
                return 1;
            }
        }
        return 0;
    }
}
