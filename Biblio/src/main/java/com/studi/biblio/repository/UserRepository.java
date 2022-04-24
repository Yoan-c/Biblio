package com.studi.biblio.repository;

import com.studi.biblio.model.User;
import com.studi.biblio.service.JdbcpConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {
    void setDatasource(JdbcpConfig datasource);
    public List<User> selectAll();
    public List<User> getUserByMail(String mail);
    public User getUserByMail(Map<String, String> userAccount);
    public boolean createUser(Map<String, String> infoUser);

}