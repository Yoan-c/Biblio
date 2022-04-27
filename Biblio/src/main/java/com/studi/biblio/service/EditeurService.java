package com.studi.biblio.service;

import com.studi.biblio.model.Editeur;
import com.studi.biblio.model.Livre;
import com.studi.biblio.repository.EditeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

@Service
public class EditeurService implements EditeurRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<Editeur> getAll() {
        String req = "SELECT * FROM editeur";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Editeur(
                rs.getLong("id"),
                rs.getString("nom")
        )));
    }
    public List<String> getEditeurById(int idEditor){
        String req = "SELECT nom FROM editeur WHERE id = ?";
        List<String> editor =  (JdT.query(req, new Object[]{idEditor}, new int[]{INTEGER}, (rs, rowNum) ->
                new String(rs.getString("nom"))));
        if (editor != null)
            return editor;
        return null;
    }

}
