package com.studi.biblio.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studi.biblio.model.Livre;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
@Repository
public interface LivreRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<Livre> getAll();
    public List<Object> getAllinfoBook(List<Livre> lstLivre);
    public String getAllinfoBook2(List<Livre> lstLivre) throws JsonProcessingException;
    public void verifyReservation(String stock, String isbn, String idUser, String mail);
    public String searchBookInfo(String titre, String auteur, String genre, String langue) throws IOException;

}
