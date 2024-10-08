package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.FilmeDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
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
import io.swagger.annotations.*;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
@CrossOrigin
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

    @GetMapping()
    public ResponseEntity get() {
        List<Filme> filmes = service.getFilmes();
        return ResponseEntity.ok(filmes.stream().map(FilmeDTO::create).collect(Collectors.toList()));
    }
    @ApiOperation("Obter informação de um filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme encontrado", response = Filme.class),
            @ApiResponse(code = 404, message = "Filme não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filme.map(FilmeDTO::create));
    }

    @ApiOperation("Incluir um filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Filme não encontrado")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody FilmeDTO dto) {
        try {
            Filme filme = converter(dto);
            filme = service.salvar(filme);
            return new ResponseEntity(filme, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Atualizar um filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Filme não encontrado")
    })
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeDTO dto) {
        if (!service.getFilmeById(id).isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Filme filme = converter(dto);
            filme.setId(id);
            filme = service.salvar(filme);
            return ResponseEntity.ok(filme);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Excluir um filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme encontrado", response = Cinema.class),
            @ApiResponse(code = 404, message = "Filme não encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.getFilmeById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filme.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
