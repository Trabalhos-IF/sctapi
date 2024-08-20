package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;

import br.edu.ifsudestemg.sctapi.api.dto.CinemaDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Cinema;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/administradores")
@RequiredArgsConstructor
@CrossOrigin
public class AdministradorController{

    private final AdministradorService service;
    public Administrador converter(AdministradorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Administrador.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Administrador> administradores = service.getAdministradores();
        return ResponseEntity.ok(administradores.stream().map(AdministradorDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de um administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "administrador encontrado", response = AdministradorDTO.class),
            @ApiResponse(code = 404, message = "administrador não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(administrador.map(AdministradorDTO::create));
    }

    @ApiOperation("Incluir um administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "administrador encontrado", response = AdministradorDTO.class),
            @ApiResponse(code = 404, message = "administrador não encontrado")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody AdministradorDTO dto) {
        try {
            Administrador administrador = converter(dto);
            administrador = service.salvar(administrador);
            return new ResponseEntity(administrador, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Atualizar um administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "administrador encontrado", response = AdministradorDTO.class),
            @ApiResponse(code = 404, message = "administrador não encontrado")
    })
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AdministradorDTO dto) {
        if (!service.getAdministradorById(id).isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Administrador administrador = converter(dto);
            administrador.setId(id);
            administrador = service.salvar(administrador);
            return ResponseEntity.ok(administrador);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Deletar um administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "administrador encontrado", response = AdministradorDTO.class),
            @ApiResponse(code = 404, message = "administrador não encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Administrador> administrador = service.getAdministradorById(id);
        if (!administrador.isPresent()) {
            return new ResponseEntity("Administrador não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(administrador.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
