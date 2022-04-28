package com.studi.biblio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "token")
public class Token{

    @Id
    private Long id;
    private String mailUser;
    private String token;
    private Timestamp validity;
    private String idToken;


    private  void generateValidity() {//génère une date de fin de validité. La ligne commentée est celle qui devrait être utilisé en théorie, mais pour faire des tests, il vaut mieux utiliser une validité plus courte.
        //this.validity = LocalDateTime.now().plusHours(24); //La validité de notre Token est de 24 heures
      //  this.validity =
        Date dateNow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dateNow);
        c.add(Calendar.SECOND, 4);
        this.validity = new Timestamp(c.getTime().getTime());; //La validité de notre Token est de 4 secondes...
    }

    public Token(User user) {
        this.mailUser = user.getEmail();
        generateValidity();
    }

    public boolean isValid(){
        Date dateNow = new Date();
        Date dateBefore = new Date();
        Calendar now = Calendar.getInstance();
        Calendar before = Calendar.getInstance();
        before.setTime(validity);
        if (now.compareTo(before) < 0)
            return true;
        else
        return false;
    }
}
