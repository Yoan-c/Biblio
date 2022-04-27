package com.studi.biblio.service;

import com.studi.biblio.model.Editeur;
import com.studi.biblio.model.Exemplaire;
import com.studi.biblio.repository.ExemplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.Types.*;

@Service
public class ExemplaireService implements ExemplaireRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }


    @Override
    public List<Exemplaire> getAll() {
        String req = "SELECT * FROM exemplaire";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Exemplaire(
                rs.getLong("id"),
                rs.getString("isbn"),
                rs.getBoolean("pret")
        )));
    }
    public List<Exemplaire> getExemplaireById(String isbn) {
        if(isbn != null && isbn.getClass() == String.class) {
            String req = "SELECT * FROM exemplaire WHERE isbn = ?";
            return new ArrayList<>(JdT.query(req, new Object[]{isbn}, new int[]{VARCHAR}, (rs, rowNum) -> new Exemplaire(
                    rs.getLong("id"),
                    rs.getString("isbn"),
                    rs.getBoolean("pret")
            )));
        }
        else
            return null;
    }
    public List<Exemplaire> getExemplaireDispoById(String isbn, boolean pret) {
        if(isbn != null && isbn.getClass() == String.class) {
            String req = "SELECT * FROM exemplaire WHERE pret = ? AND isbn = ?";
            return new ArrayList<>(JdT.query(req, new Object[]{pret, isbn}, new int[]{BOOLEAN,VARCHAR}, (rs, rowNum) -> new Exemplaire(
                    rs.getLong("id"),
                    rs.getString("isbn"),
                    rs.getBoolean("pret")
            )));
        }
        else
            return null;
    }

    public void setExemplaireDispoById(String isbn, boolean pret, Long id) {
        if(isbn != null && isbn.getClass() == String.class) {
            String req = "UPDATE `exemplaire` SET `pret`=? WHERE id = ?";
            JdT.update(req, new Object[]{pret, id}, new int[]{BOOLEAN,INTEGER});
        }
    }

    public int addExemplaireById(String isbn) {
        if(isbn != null && isbn.getClass() == String.class) {
            String req = "INSERT INTO exemplaire(isbn) VALUES (?)";
            JdT.update(req, isbn);
            return 1;
        }
        return -1;
    }

    public void deleteExemplaireByID(String id){
        Logger logger = Logger.getLogger("logger");

        logger.log(Level.INFO, "deleteExemplaireByID  de logger :" + id);
        String req = "DELETE FROM `exemplaire` WHERE id = ?";
        JdT.update(req, id);
    }

}
