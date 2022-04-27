package com.studi.biblio.repository;

import com.studi.biblio.model.Genre;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository {
    void setDatasource(JdbcpConfig datasource);
    public List<Genre> getAll();
}
