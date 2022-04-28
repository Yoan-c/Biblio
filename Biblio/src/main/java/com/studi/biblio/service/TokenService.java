package com.studi.biblio.service;

import com.studi.biblio.model.Token;
import com.studi.biblio.model.User;
import com.studi.biblio.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.VARCHAR;

@Service
public class TokenService implements TokenRepository {
    @Autowired
    private JdbcTemplate JdT;

    @Override
    public void setDatasource(JdbcpConfig datasource) {
        this.JdT = new JdbcTemplate(datasource.MysqlDataSource());
    }


    public List<Token> selectAll() {
        String req = "SELECT * FROM token";
        return new ArrayList<>(JdT.query(req, (rs, rowNum) -> new Token(
                rs.getLong("id"),
                rs.getString("mailUser"),
                rs.getString("token"),
                rs.getTimestamp("validity"),
                rs.getString("idToken")
        )));
    }

    @Override
    public List<Token> getTokenByMail(String mail) {
        String req = "SELECT * FROM token WHERE mailUser = ?";
        return new ArrayList<>(JdT.query(req, new Object[]{mail}, new int[]{VARCHAR},(rs, rowNum) -> new Token(
                rs.getLong("id"),
                rs.getString("mailUser"),
                rs.getString("token"),
                rs.getTimestamp("validity"),
                rs.getString("idToken")
        )));
    }
    public List<Token> getTokenByIdToken(String idToken) {
        String req = "SELECT * FROM token WHERE idToken = ?";
        return new ArrayList<>(JdT.query(req, new Object[]{idToken}, new int[]{VARCHAR},(rs, rowNum) -> new Token(
                rs.getLong("id"),
                rs.getString("mailUser"),
                rs.getString("token"),
                rs.getTimestamp("validity"),
                rs.getString("idToken")
        )));
    }
    public void setToken(String mail, String idToken, Token token, String tkn){
        String requete = "INSERT INTO token (mailUser, validity, idToken, token) VALUES (?, ?, ?, ?)";
        JdT.update(requete, mail, token.getValidity(), idToken, tkn);
    }
    public void updateToken(String mail, String idToken, Token token, String tkn){
        String requete = "UPDATE token SET validity=?, idToken=?, token=? WHERE mailUser=?";
        JdT.update(requete, token.getValidity(), idToken, tkn, mail);
    }
}
