package com.studi.biblio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean renouvellement;

    @Column (name = "date_debut")
    private Date startDate;

    @Column (name = "date_fin")
    private Date endDate;

    @Column (name = "id_utilisateur")
    private long idUser;

    @Column (name = "id_exemplaire")
    private long idExemplaire;

}
