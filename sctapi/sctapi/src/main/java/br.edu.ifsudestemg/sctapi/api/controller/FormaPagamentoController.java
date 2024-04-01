package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.FormaPagamentoDTO;

import br.edu.ifsudestemg.sctapi.model.entity.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/formaPagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {
    public FormaPagamento converter(FormaPagamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, FormaPagamento.class);
    }

}