package com.studi.biblio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;
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

    private Date date_publication;

    private String description;

    private String langue;

    @Column (name = "couverture")
    private String cover;

    @Override
    public String toString()
    {
        return "Book [isbn="
                +isbn
                + ", bookName="
                +  title
                + ", editor="
                + editor
                +", datePublication ="
                +date_publication
                +", desc="
                +description
                +", langue="
                +langue
                +", cover="
                +cover+"]";
    }
}
