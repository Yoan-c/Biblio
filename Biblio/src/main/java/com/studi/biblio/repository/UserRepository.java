package com.studi.biblio.repository;

import com.studi.biblio.model.Pret;
import com.studi.biblio.model.User;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {

    void setDatasource(JdbcpConfig datasource);
    public List<User> selectAll();
    public List<User> getUserByMail(String mail);
    public User getUserByMail(Map<String, String> userAccount);
    public boolean createUser(Map<String, String> infoUser);
    public List<Map<String, Object>> getListPret(String id);
    public int reserveBook(HttpServletRequest request, Map<String, String> info, HttpSession session);

    public int updateUser(Map<String, String> infoUser, String idUser);


}