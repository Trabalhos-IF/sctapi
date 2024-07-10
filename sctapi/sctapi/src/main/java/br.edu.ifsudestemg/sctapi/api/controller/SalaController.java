package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.SalaDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Sala;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService service;

    public Sala converter(SalaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Sala.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Sala> salas = service.getSalas();
        return ResponseEntity.ok(salas.stream().map(SalaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSalaById(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Tipo de Ticket não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sala.map(SalaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SalaDTO dto) {
        try {
            Sala sala = converter(dto);
            sala = service.salvar(sala);
            return new ResponseEntity(sala, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SalaDTO dto) {
        if (!service.getSalaById(id).isPresent()) {
            return new ResponseEntity("Sala não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Sala sala = converter(dto);
            sala.setId(id);
            sala = service.salvar(sala);
            return ResponseEntity.ok(sala);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSalaById(id);
        if (!Sala.isPresent()) {
            return new ResponseEntity("Sala não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sala.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
