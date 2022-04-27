package com.studi.biblio.repository;


import com.studi.biblio.model.LivreAuteur;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreAuteurRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<LivreAuteur> getAll();
}
