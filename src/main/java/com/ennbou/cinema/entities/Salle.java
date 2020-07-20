package com.ennbou.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Salle {
    public static final String FK_ID = "id_salle";
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int nombrePlaces;

    @ManyToOne
    @JoinColumn(name = Cinema.FK_ID)
    private Cinema cinema;

    @OneToMany(mappedBy = "salle", fetch = FetchType.EAGER)
    private List<Place> places;

    @OneToMany(mappedBy = "salle")
    private List<ProjectionFilm> projectionFilms;

}
