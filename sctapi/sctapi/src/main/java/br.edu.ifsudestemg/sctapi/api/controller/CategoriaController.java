package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.CategoriaDTO;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Assento;
import br.edu.ifsudestemg.sctapi.model.entity.Categoria;
import br.edu.ifsudestemg.sctapi.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@CrossOrigin
public class CategoriaController {

    private final CategoriaService service;
    public Categoria converter(CategoriaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Categoria.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Categoria> categoria = service.getCategorias();
        return ResponseEntity.ok(categoria.stream().map(CategoriaDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada", response = Categoria.class),
            @ApiResponse(code = 404, message = "Categoria não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categoria.map(CategoriaDTO::create));
    }

    @ApiOperation("Incluir uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada", response = Categoria.class),
            @ApiResponse(code = 404, message = "Categoria não encontrada")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody CategoriaDTO dto) {
        try {
            Categoria categoria = converter(dto);
            categoria = service.salvar(categoria);
            return new ResponseEntity(categoria, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Atualizar uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada", response = Categoria.class),
            @ApiResponse(code = 404, message = "Categoria não encontrado")
    })
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
        if (!service.getCategoriaById(id).isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Categoria categoria = converter(dto);
            categoria.setId(id);
            categoria = service.salvar(categoria);
            return ResponseEntity.ok(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Excluir uma categoria")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Categoria encontrada", response = Categoria.class),
            @ApiResponse(code = 404, message = "Categoria não encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = service.getCategoriaById(id);
        if (!categoria.isPresent()) {
            return new ResponseEntity("Categoria não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(categoria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
