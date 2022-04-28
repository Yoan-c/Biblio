package com.studi.biblio.repository;

import com.studi.biblio.model.LivreGenre;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreGenreRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<LivreGenre> getAll();

    List<LivreGenre> getByName(String name);
}
