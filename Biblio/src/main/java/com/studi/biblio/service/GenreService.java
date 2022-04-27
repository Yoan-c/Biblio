package com.studi.biblio.service;

import com.studi.biblio.model.Genre;
import com.studi.biblio.model.Livre;
import com.studi.biblio.repository.AuteurRepository;
import com.studi.biblio.repository.EditeurRepository;
import com.studi.biblio.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService implements GenreRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<Genre> getAll() {
        String req = "SELECT * FROM genre";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Genre(
                rs.getString("nom")
        )));
    }
}
