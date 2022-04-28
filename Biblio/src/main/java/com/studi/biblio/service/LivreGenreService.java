package com.studi.biblio.service;

import com.studi.biblio.model.LivreGenre;
import com.studi.biblio.repository.LivreGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

@Service
public class LivreGenreService implements LivreGenreRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }

    @Override
    public List<LivreGenre> getAll() {
        String req = "SELECT * FROM livre_genre";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new LivreGenre(
                rs.getString("isbn"),
                rs.getString("nom_genre")
        )));
    }

    @Override
    public List<LivreGenre> getByName(String name) {
        String req = "SELECT * FROM livre_genre where nom_genre = ?";
        return new ArrayList<>(JdT.query(req, new Object[]{name},new int[]{VARCHAR},(rs, rowNum) -> new LivreGenre(
                rs.getString("isbn"),
                rs.getString("nom_genre")
        )));
    }
}
