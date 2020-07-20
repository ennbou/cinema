package com.ennbou.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Projection(name = "p1", types = {ProjectionFilm.class})
public interface ProjectionProj{
    public Long getId();
    public double getPrix();
    public LocalDate getDateProjection();
    public Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public List<Ticket> getTickets();
}
