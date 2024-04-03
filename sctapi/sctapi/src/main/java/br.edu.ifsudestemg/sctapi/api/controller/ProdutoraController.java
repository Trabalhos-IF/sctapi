package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.CategoriaDTO;
import br.edu.ifsudestemg.sctapi.api.dto.ProdutoraDTO;

import br.edu.ifsudestemg.sctapi.model.entity.Categoria;
import br.edu.ifsudestemg.sctapi.model.entity.Produtora;
import br.edu.ifsudestemg.sctapi.service.ProdutoraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/produtoras")
@RequiredArgsConstructor
public class ProdutoraController {

    private final ProdutoraService service;
    public Produtora converter(ProdutoraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produtora.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Produtora> produtora = service.getProdutoras();
        return ResponseEntity.ok(produtora.stream().map(ProdutoraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Produtora> produtora = service.getProdutoraById(id);
        if (!produtora.isPresent()) {
            return new ResponseEntity("Produtora não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produtora.map(ProdutoraDTO::create));
    }
}
