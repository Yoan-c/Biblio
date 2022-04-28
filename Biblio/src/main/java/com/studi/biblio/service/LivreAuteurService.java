package com.studi.biblio.service;

import com.studi.biblio.model.Exemplaire;
import com.studi.biblio.model.LivreAuteur;
import com.studi.biblio.repository.LivreAuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

@Service
public class LivreAuteurService implements LivreAuteurRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }


    @Override
    public List<LivreAuteur> getAll() {
        String req = "SELECT * FROM livre_auteur";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new LivreAuteur(
                rs.getString("isbn"),
                rs.getLong("id_auteur")
        )));
    }
    public List<LivreAuteur> getAllIsbnByAuthorName(String nomAuteur) {
        String req = "SELECT * FROM livre_auteur la, auteur a WHERE la.id_auteur = a.id AND a.nom LIKE ";
        String n = "'"+nomAuteur+"%'";
        req += n;

        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new LivreAuteur(
                rs.getString("isbn"),
                rs.getLong("id_auteur")
        )));
    }
}
