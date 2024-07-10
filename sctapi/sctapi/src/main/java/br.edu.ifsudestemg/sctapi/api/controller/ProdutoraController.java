package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.CategoriaDTO;
import br.edu.ifsudestemg.sctapi.api.dto.ProdutoraDTO;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
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

    @PostMapping()
    public ResponseEntity post(@RequestBody ProdutoraDTO dto) {
        try {
            Produtora produtora = converter(dto);
            produtora = service.salvar(produtora);
            return new ResponseEntity(produtora, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoraDTO dto) {
        if (!service.getProdutoraById(id).isPresent()) {
            return new ResponseEntity("Produtora não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produtora produtora = converter(dto);
            produtora.setId(id);
            produtora = service.salvar(produtora);
            return ResponseEntity.ok(produtora);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Produtora> produtora = service.getProdutoraById(id);
        if (!produtora.isPresent()) {
            return new ResponseEntity("Produtora não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(produtora.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
