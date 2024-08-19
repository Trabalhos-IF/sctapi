package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.SessaoDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Sessao;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.SessaoService;
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
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@CrossOrigin
public class SessaoController {

    private final SessaoService service;

    public Sessao converter(SessaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Sessao.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Sessao> sessoes = service.getSessoes();
        return ResponseEntity.ok(sessoes.stream().map(SessaoDTO::create).collect(Collectors.toList()));
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessão encontrada", response = Sessao.class),
            @ApiResponse(code = 404, message = "Sessão não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessao.map(SessaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SessaoDTO dto) {
        try {
            Sessao sessao = converter(dto);
            sessao = service.salvar(sessao);
            return new ResponseEntity(sessao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessaoDTO dto) {
        if (!service.getSessaoById(id).isPresent()) {
            return new ResponseEntity("Sessao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Sessao sessao = converter(dto);
            sessao.setId(id);
            sessao = service.salvar(sessao);
            return ResponseEntity.ok(sessao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.getSessaoById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
