package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.SalaDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
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
}