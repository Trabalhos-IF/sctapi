package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.ProdutoraDTO;

import br.edu.ifsudestemg.sctapi.model.entity.Produtora;
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
    public Produtora converter(ProdutoraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produtora.class);
    }
}
