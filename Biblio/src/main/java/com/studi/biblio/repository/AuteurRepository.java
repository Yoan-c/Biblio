package com.studi.biblio.repository;

import com.studi.biblio.model.Auteur;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuteurRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<Auteur> getAll();
}
