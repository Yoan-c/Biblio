package com.studi.biblio.repository;


import com.studi.biblio.model.Exemplaire;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<Exemplaire> getAll();
    public List<Exemplaire> getExemplaireById(String isbn);
    public int addExemplaireById(String isbn);
    public void deleteExemplaireByID(String id);
    public List<Exemplaire> getExemplaireDispoById(String isbn, boolean pret);
    public void setExemplaireDispoById(String isbn, boolean pret, Long id);
}
