package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoExibicaoDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.TipoExibicao;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.TipoExibicaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tiposExibicoes")
@RequiredArgsConstructor
@CrossOrigin
public class TipoExibicaoController {

    private final TipoExibicaoService service;

    public TipoExibicao converter(TipoExibicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoExibicao.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<TipoExibicao> tipoExibicoes = service.getTiposExibicao();
        return ResponseEntity.ok(tipoExibicoes.stream().map(TipoExibicaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoExibicao> tipoExibicao = service.getTipoExibicaoById(id);
        if (!tipoExibicao.isPresent()) {
            return new ResponseEntity("Tipo de Exibição não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipoExibicao.map(TipoExibicaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody TipoExibicaoDTO dto) {
        try {
            TipoExibicao tipoExibicao = converter(dto);
            tipoExibicao = service.salvar(tipoExibicao);
            return new ResponseEntity(tipoExibicao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoExibicaoDTO dto) {
        if (!service.getTipoExibicaoById(id).isPresent()) {
            return new ResponseEntity("TipoExibicao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoExibicao tipoExibicao = converter(dto);
            tipoExibicao.setId(id);
            tipoExibicao = service.salvar(tipoExibicao);
            return ResponseEntity.ok(tipoExibicao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoExibicao> tipoExibicao = service.getTipoExibicaoById(id);
        if (!tipoExibicao.isPresent()) {
            return new ResponseEntity("TipoExibicao não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoExibicao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
