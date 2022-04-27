package com.studi.biblio.repository;

import com.studi.biblio.model.Exemplaire;
import com.studi.biblio.model.Pret;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface PretRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<Pret> getAll();
    public List<Map<String, Object>> getPretById(String id);
    public int checkEmpruntBook(List<Map<String, Object>> lst, String isbn, String stock, String idUser);
    public void addPretBook(String isbn,  List<Exemplaire> lst, String id, boolean renouvellement);
    public ArrayList<Object> getPretAllInfo(String id);

    public int setProlongation(String isbn, String idUser, boolean b);
}
