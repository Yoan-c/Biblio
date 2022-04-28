package com.studi.biblio.model;

import com.studi.biblio.encode.Empreinte;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.VARCHAR;

/*
    Classe utilisateur
 */
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom")
    private String lastName;

    @Column(name = "prenom")
    private String firstName;

    private String email;

    @Column(name="mdp")
    private String password;

    private String sel;

    private static final Map<String, User> users = new HashMap<>();

    public User(String mail, String hashSalt, String salt) {
        this.setEmail(mail);
        this.setSel(salt);
        this.setPassword(hashSalt);
        firstName = "";
        lastName = "";
    }

}