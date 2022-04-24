package com.studi.biblio.service;

import com.studi.biblio.encode.Empreinte;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.sql.Types.VARCHAR;


@Service
public class UserService implements UserRepository {

    @Autowired
    private JdbcTemplate JdT;
    private Empreinte e;

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
}
