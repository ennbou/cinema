package com.ennbou.cinema.controller;

import com.ennbou.cinema.dao.FilmRepository;
import com.ennbou.cinema.dao.TicketRepository;
import com.ennbou.cinema.entities.Film;
import com.ennbou.cinema.entities.Ticket;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin("*")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TicketRepository ticketRepository;

//    @GetMapping(path="/film/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
//
//    public byte[] image(@PathVariable (name = "id")Long id)throws Exception{
//        Film f=filmRepository.findById(id).get();
//        String pathImage = f.getPhoto();
//
//        File file = new File(System.getProperty("user.home")+"/Documents/PI/imgs/"+ id + "." + pathImage);
//        Path path= Paths.get(file.toURI());
//        return Files.readAllBytes(path);
//    }

    @GetMapping(path = "/film/img/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable("id") Long id) throws IOException {
        Film film = filmRepository.findById(id).get();
        String pathImage = film.getPhoto();
        File file = new File("C:/Users/ENNBO/Documents/PI/imgs/" + id + "." + pathImage);
        return Files.readAllBytes(Paths.get(file.toURI()));
    }

    @RequestMapping(path = "/projection-film/{id}")
    public @ResponseBody
    List<Ticket> buyTicket(@PathVariable(name = "id") Long id,@RequestBody PayForm payFrom) {
        System.out.println("nom client : "+payFrom.getNomClient());
        List<Ticket> tickets = new ArrayList<>();
        ticketRepository.findTest(id).subList(0, payFrom.getNbr()).forEach(ticket -> {
            ticket.setReserve(true);
            ticket.setNomClient(payFrom.getNomClient());
            ticket.setCodePaiement((int)(Math.random()*(100-10)+100));
            ticketRepository.save(ticket);
            tickets.add(ticket);
        });
        return tickets;
    }

}

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
class PayForm {
    private String nomClient;
    private int nbr;
}

