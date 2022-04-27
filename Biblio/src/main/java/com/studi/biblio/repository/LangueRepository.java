package com.studi.biblio.repository;

import com.studi.biblio.model.Langue;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LangueRepository {
    void setDatasource(JdbcpConfig datasource);
    public List<Langue> getAll();
}
