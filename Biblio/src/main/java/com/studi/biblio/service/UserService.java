package com.studi.biblio.service;

import com.studi.biblio.encode.Empreinte;
import com.studi.biblio.model.Pret;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.PretRepository;
import com.studi.biblio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.sql.Types.VARCHAR;


@Service
public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate JdT;
    private Empreinte e;

    @Autowired
    private PretRepository pr;

    private static Pattern pattern;
    private static Matcher matcher;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }


    public List<User> selectAll() {
        String req = "SELECT * FROM utilisateur";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            rs.getString("mdp"),
            rs.getString("sel")
        )));
    }

    @Override
    public List<User> getUserByMail(String mail) {

        if (mail != null && !mail.equals("")) {
            String requete = "SELECT * FROM utilisateur WHERE email = ?";
            List<User> ls =  (JdT.query(requete, new Object[]{mail}, new int[]{VARCHAR}, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    rs.getString("sel")
            )));
            if(!ls.isEmpty())
                return ls;
        }
        return null;
    }

    @Override
    public User getUserByMail(Map<String, String> userAccount) {
        List<User> user = getUserByMail(userAccount.get("mail"));
        if(user != null) {
            Empreinte e = new Empreinte();
            boolean is_valid = e.isValidPassword(user.get(0).getPassword(), userAccount.get("password"), user.get(0).getSel());
            return (is_valid) ? user.get(0) : null;
        }
        return null;
    }

    public void addUser (User user) {
        String requete = "INSERT INTO utilisateur (nom, prenom, email, mdp, sel) VALUES (?, ?, ?, ?, ?)";
        JdT.update(requete,user.getLastName(), user.getFirstName(), user.getEmail(), user.getPassword(),
        user.getSel());
    }
    public void updateWithParam (Map<String, String> infoUser, String idUser) {

        String requete = "UPDATE utilisateur SET nom=?,prenom=?,email=?,mdp=?,sel=? WHERE id = ?";
        JdT.update(requete,infoUser.get("lastName"), infoUser.get("firstName"), infoUser.get("mail"), infoUser.get("mdp"),
                infoUser.get("sel"), idUser);

    }

    /*
    * fonction qui prend un string et un nombre en parametre.
    * Verifie la taille d'un type string
    * */
    public String verifLength(String champs, int limit){
        String newChamps;
        if(champs.length() > limit)
            newChamps = champs.substring(0, limit);
        else
            newChamps = champs;
        return newChamps;
    }
    @Override
    public boolean createUser(Map<String, String> infoUser) {
        boolean isMatch;

        pattern = Pattern.compile("^[\\w-\\.]{3,20}@([\\w-]{2,20}\\.)[\\w-]{2,4}$");
        matcher = pattern.matcher(infoUser.get("mail"));
        isMatch = matcher.matches();

        if(isMatch) {
            if (infoUser.get("password").equals(infoUser.get("password_confirm"))) {
                infoUser.put("firstName", verifLength(infoUser.get("firstName"), 20));
                infoUser.put("lastName", verifLength(infoUser.get("lastName"), 20));
                User newUser = new User();
                newUser.setFirstName(infoUser.get("firstName"));
                newUser.setLastName(infoUser.get("lastName"));
                newUser.setEmail(infoUser.get("mail"));
                e = new Empreinte();
                String salt = e.getSalt(56);
                newUser.setSel(salt);
                newUser.setPassword(e.getHashSalt(infoUser.get("password"), salt));
                if(getUserByMail(infoUser.get("mail")) == null) {
                   addUser(newUser);
                   return true;
                }
            }
        }
        return false;
    }
    public List<Map<String, Object>> getListPret(String id){
        if (id != null && !id.equals("")) {
            List<Map<String, Object>> lst = pr.getPretById(id);
            return lst;
        }
        return null;
    }


    /*
     -1 erreur
     0 emprunt impossible
     1 emprunt
     2 deja emprunter refus
     */
    public int reserveBook(HttpServletRequest req, Map<String, String> info, HttpSession session){
        String mail = (String) session.getAttribute("mail");
        String idUser = (String) session.getAttribute("USER_SESSION");
        String stock = req.getParameter("stock");
        String isbn = req.getParameter("isbn");
        System.out.println(" info " + mail+" "+idUser+" "+stock+" "+ isbn);
        if(isbn == null || isbn.equals("") || stock == null || Integer.parseInt(stock) < 0) {
            return -1;
        }
        List<Map<String, Object>> lst = getListPret(idUser);
        if(lst == null ) {
            return -1;
        }
        return pr.checkEmpruntBook(lst, isbn, stock, idUser);
    }

    @Override
    public int updateUser(Map<String, String> infoUser, String idUser) {
        Logger logger = Logger.getLogger("");
        if(infoUser.get("lastName") == null || infoUser.get("firstName") == null || infoUser.get("mail") == null ||
        infoUser.get("mdp") == null || infoUser.get("mdpConfirm") == null || !infoUser.get("mdp").equals(infoUser.get("mdpConfirm"))){
                return -1;
        } else {
            updateUserWithParam(infoUser, idUser);
            return 1;
        }
    }
    public void updateUserWithParam(Map<String, String> infoUser, String idUser){
        e = new Empreinte();
        String salt = e.getSalt(56);
        infoUser.put("mdp", e.getHashSalt(infoUser.get("mdp"), salt));
        infoUser.put("sel", salt);
        updateWithParam(infoUser, idUser);
    }
}
