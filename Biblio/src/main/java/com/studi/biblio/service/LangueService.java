package com.studi.biblio.service;

import com.studi.biblio.model.Langue;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.LangueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LangueService implements LangueRepository {
    @Autowired
    private JdbcTemplate JdT;
    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }


    public List<Langue> getAll() {
        String req = "SELECT * FROM langue";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Langue(
                rs.getString("nom")
        )));
    }
}
