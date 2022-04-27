package com.studi.biblio.repository;

import com.studi.biblio.model.Editeur;
import com.studi.biblio.model.Livre;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LivreRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<Livre> getAll();
    public List<Object> getAllinfoBook();
    public void verifyReservation(String stock, String isbn, String idUser, String mail);
    public void searchBookInfo(String titre, String auteur, String genre, String langue);

}
