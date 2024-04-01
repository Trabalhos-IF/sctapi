package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.CinemaDTO;

import br.edu.ifsudestemg.sctapi.model.entity.Cinema;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;

import  br.edu.ifsudestemg.sctapi.service.CinemaService;
import  br.edu.ifsudestemg.sctapi.service.AdministradorService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor

public class CinemaController {
    private final CinemaService service;
    private final AdministradorService admService;

    public Cinema converter(CinemaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cinema cinema = modelMapper.map(dto, Cinema.class);

        if (dto.getIdAdministrador() != null) {
            Optional<Administrador> administrador = admService.getAdministradorById(dto.getIdAdministrador());
            if (!administrador.isPresent()) {
                cinema.setAdministrador(null); // Use the assento object
            } else {
                cinema.setAdministrador(administrador.get()); // Use the assento object
            }
        }
        return cinema;
    }
}
