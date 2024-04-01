package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.FilmeDTO;

import br.edu.ifsudestemg.sctapi.model.entity.*;

import br.edu.ifsudestemg.sctapi.service.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
public class FilmeController {
    private final FilmeService service;
    private final ProdutoraService produtoraService;
    private final CategoriaService categoriaService;
    public Filme converter(FilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Filme filme = modelMapper.map(dto, Filme.class);

        if (dto.getIdProdutora() != null) {
            Optional<Produtora> produtora = produtoraService.getProdutoraById(dto.getIdProdutora());

            produtora.ifPresent(filme::setProdutora);
        }

        if (dto.getIdCategoria() != null) {
            Optional<Categoria> categoria = categoriaService.getCategoriaById(dto.getIdCategoria());

            categoria.ifPresent(filme::setCategoria);
        }
        return filme;
    }
}
