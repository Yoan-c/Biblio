package com.studi.biblio.repository;

import com.studi.biblio.model.Token;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface TokenRepository {
    void setDatasource(JdbcpConfig datasource);

    public List<Token> selectAll();

    public List<Token> getTokenByMail(String mail);

    public void setToken(String Email, String idToken, Token token, String tkn);
    public List<Token> getTokenByIdToken(String idToken);
    public void updateToken(String Email, String idToken, Token token, String tkn);
}
