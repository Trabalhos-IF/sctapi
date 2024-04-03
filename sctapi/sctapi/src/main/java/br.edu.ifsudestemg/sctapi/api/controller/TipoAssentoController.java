package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.TipoAssentoDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.model.entity.TipoAssento;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.TipoAssentoService;
import br.edu.ifsudestemg.sctapi.service.TipoTicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoAssento")
@RequiredArgsConstructor
public class TipoAssentoController {

    private final TipoAssentoService service;

    public TipoAssento converter(TipoAssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoAssento.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<TipoAssento> tipoAssentos = service.getTiposAssento();
        return ResponseEntity.ok(tipoAssentos.stream().map(TipoAssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo de assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoAssento.map(TipoAssentoDTO::create));
    }
}
