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
public class Cinema {

    public static final String FK_ID = "id_cinema";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double longitude;
    private double latitude;
    private double altitude;
    private int nombreSalles;

    @ManyToOne
    private Ville ville;
    
    @OneToMany(mappedBy = "cinema")
    private List<Salle> salles;
}
