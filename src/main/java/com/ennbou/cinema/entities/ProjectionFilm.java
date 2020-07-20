package com.ennbou.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ProjectionFilm {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateProjection;
    private double prix;


    @OneToMany(mappedBy = "projectionFilm")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = Seance.FK_ID)
    private Seance seance;

    @ManyToOne
    @JoinColumn(name = Salle.FK_ID)
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = Film.FK_ID)
    private Film film;


}
