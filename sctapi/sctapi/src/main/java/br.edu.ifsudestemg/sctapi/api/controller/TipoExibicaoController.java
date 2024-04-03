package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.TipoExibicaoDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
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
@RequestMapping("/api/v1/tipoExibicao")
@RequiredArgsConstructor
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
}
