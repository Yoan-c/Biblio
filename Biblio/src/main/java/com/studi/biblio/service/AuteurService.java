package com.studi.biblio.service;

import com.studi.biblio.model.Auteur;
import com.studi.biblio.model.Livre;
import com.studi.biblio.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuteurService implements AuteurRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<Auteur> getAll() {
        String req = "SELECT * FROM auteur";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Auteur(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("prenom")
        )));
    }
}
