package com.studi.biblio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name ="livre")
public class Livre {

    @Id
    private String isbn;

    @Column(name = "titre")
    private String title;

    @Column (name = "editeur")
    private Long editor;

    private Timestamp date_publication;

    private String description;

    private String langue;

    @Column (name = "couverture")
    private String cover;
}
