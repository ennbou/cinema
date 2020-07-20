package com.ennbou.cinema.dao;

import com.ennbou.cinema.entities.ProjectionFilm;
import com.ennbou.cinema.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionFilmRepository extends JpaRepository<ProjectionFilm, Long> {
    List<ProjectionFilm> findBySalle(Salle salle);
}
