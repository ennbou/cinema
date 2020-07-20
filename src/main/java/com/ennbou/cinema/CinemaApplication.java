package com.ennbou.cinema;


import com.ennbou.cinema.dao.*;
import com.ennbou.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.time.LocalTime.*;


@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {


    @Autowired
    RepositoryRestConfiguration repositoryRestConfiguration;

    @Autowired
    VilleRepository villeRepository;
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    SeanceRepository seanceRepository;
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    SalleRepository salleRepository;
    @Autowired
    TicketRepository ticketRepository;


    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repositoryRestConfiguration.exposeIdsFor(Film.class,ProjectionFilm.class);

        String[] villes = {"ville 1", "ville 2", "ville 3"};
        String[] cinemas = {"cinema 1", "cinema 2"};
        String[] salles = {"salle a", "salle b","salle c","salle d","salle e"};
        int[] places = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] categories = {"categorie 1", "categorie 2"};
        String[] films = {"film 1", "film 2", "film 3", "film 4", "film 5"};
        LocalTime[] heuresDebut = {of(9, 30), of(11, 30), of(15, 30), of(20, 30)};

        initVilles(villes);
        initCinemas(cinemas);
        initSalles(salles);
        initPlaces(places);
        initSeance(heuresDebut);
        initCategorie(categories);
        initFilm(films);
        initProjection();
        initTicket();


    }

    void initVilles(String[] villes) {
        for (String ville : villes) {
            Ville v = new Ville();
            v.setNom(ville);
            villeRepository.save(v);
        }
    }

    void initCinemas(String[] cinemas) {
        villeRepository.findAll().forEach(ville -> {
            for (String cinema : cinemas) {
                Cinema c = new Cinema();
                c.setNom(cinema);
                c.setNombreSalles(10);
                c.setVille(ville);
                cinemaRepository.save(c);
            }
        });
    }

    void initSalles(String[] salles) {
        cinemaRepository.findAll().forEach(cinema -> {
            for (String salle : salles) {
                Salle s = new Salle();
                s.setNom(salle);
                s.setNombrePlaces(20);
                s.setCinema(cinema);
                salleRepository.save(s);
            }
        });
    }

    void initPlaces(int[] places) {
        salleRepository.findAll().forEach(salle -> {
            for (int place : places) {
                Place p = new Place();
                p.setNumero(place);
                p.setSalle(salle);
                placeRepository.save(p);
            }
        });
    }


    void initSeance(LocalTime[] heuresDebut) {
        for (LocalTime heureDebut : heuresDebut) {
            seanceRepository.save(new Seance(null, heureDebut));
        }
    }

    void initCategorie(String[] categories) {
        for (String categorie : categories) {
            Categorie c = new Categorie();
            c.setNom(categorie);
            categorieRepository.save(c);
        }
    }

    void initFilm(String[] films) {
        categorieRepository.findAll().forEach(categorie -> {
            for (String film : films) {
                Film f = new Film();
                f.setTitre(film);
                f.setDuree(90);
                f.setDateSortie(LocalDate.of(2020, 1, 10));
                f.setCategorie(categorie);
                f.setPhoto("png");
                filmRepository.save(f);
            }
        });
    }

    void initProjection() {
        // seance film salle
        filmRepository.findAll().forEach(film -> {
            ProjectionFilm pF = new ProjectionFilm();
            pF.setDateProjection(film.getDateSortie().plusDays(20));
            pF.setPrix(300);
            pF.setSeance(seanceRepository.findAll().get(0));
            pF.setFilm(film);
            pF.setSalle(salleRepository.findAll().get(film.getId().intValue()-1));
            projectionFilmRepository.save(pF);

            ProjectionFilm pF2 = new ProjectionFilm();
            pF2.setDateProjection(film.getDateSortie().plusDays(20));
            pF2.setPrix(200);
            pF2.setSeance(seanceRepository.findAll().get(1));
            pF2.setFilm(film);
            pF2.setSalle(salleRepository.findAll().get(film.getId().intValue()-1));
            projectionFilmRepository.save(pF2);


        });

    }

    void initTicket() {

        projectionFilmRepository.findAll().forEach(projectionFilm -> {
            projectionFilm.getSalle().getPlaces().forEach(place -> {
                Ticket t = new Ticket();
                t.setPlace(place);
                t.setPrix(projectionFilm.getPrix()-10);
                t.setProjectionFilm(projectionFilm);
                ticketRepository.save(t);
            });
        });
    }

}
