package com.studi.biblio.repository;

import com.studi.biblio.model.Editeur;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EditeurRepository {
    void setDatasource(JdbcpConfig datasource);
    public List<Editeur> getAll();

    public List<String> getEditeurById(int idEditor);

}
