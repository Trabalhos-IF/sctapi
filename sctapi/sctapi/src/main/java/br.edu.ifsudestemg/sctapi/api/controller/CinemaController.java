package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.CinemaDTO;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.model.entity.Cinema;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;

import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
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
import io.swagger.annotations.*;
@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
@CrossOrigin
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

    @GetMapping()
    public ResponseEntity get() {
        List<Cinema> cinemas = service.getCinemas();
        return ResponseEntity.ok(cinemas.stream().map(CinemaDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de um cinema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cinema encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Cinema não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cinema> cinema = service.getCinemaById(id);
        if (!cinema.isPresent()) {
            return new ResponseEntity("Cinema não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cinema.map(CinemaDTO::create));
    }

    @ApiOperation("Incluir um cinema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cinema encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Cinema não encontrado")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody CinemaDTO dto) {
        try {
            Cinema cinema = converter(dto);
            cinema = service.salvar(cinema);
            return new ResponseEntity(cinema, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Atualizar um cinema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cinema encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Cinema não encontrado")
    })
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CinemaDTO dto) {
        if (!service.getCinemaById(id).isPresent()) {
            return new ResponseEntity("Cinema não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cinema cinema = converter(dto);
            cinema.setId(id);
            cinema = service.salvar(cinema);
            return ResponseEntity.ok(cinema);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Excluir um cinema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cinema encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Cinema não encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Cinema> cinema = service.getCinemaById(id);
        if (!cinema.isPresent()) {
            return new ResponseEntity("Cinema não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cinema.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
