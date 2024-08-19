package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoAssentoDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
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
import io.swagger.annotations.*;
@RestController
@RequestMapping("/api/v1/tiposAssentos")
@RequiredArgsConstructor
@CrossOrigin
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

    @ApiResponses({
            @ApiResponse(code = 200, message = "Tipo de assento encontrado", response = TipoAssento.class),
            @ApiResponse(code = 404, message = "Tipo de assento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("Tipo de assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoAssento.map(TipoAssentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody TipoAssentoDTO dto) {

        try {
            TipoAssento tipoAssento = converter(dto);
            if(dto != null){
                System.out.println("DTO: " + dto);
                System.out.println("TipoAssento: " + tipoAssento);
            }
            tipoAssento = service.salvar(tipoAssento);
            return new ResponseEntity(tipoAssento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoAssentoDTO dto) {
        if (!service.getTipoAssentoById(id).isPresent()) {
            return new ResponseEntity("TipoAssento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoAssento tipoAssento = converter(dto);
            tipoAssento.setId(id);
            tipoAssento = service.salvar(tipoAssento);
            return ResponseEntity.ok(tipoAssento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoAssento> tipoAssento = service.getTipoAssentoById(id);
        if (!tipoAssento.isPresent()) {
            return new ResponseEntity("TipoAssento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoAssento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
