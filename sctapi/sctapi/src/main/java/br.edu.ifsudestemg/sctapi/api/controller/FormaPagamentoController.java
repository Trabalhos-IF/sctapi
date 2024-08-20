package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.FormaPagamentoDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.service.FormaPagamentoService;
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
@RequestMapping("/api/v1/formaPagamentos")
@RequiredArgsConstructor
@CrossOrigin
public class FormaPagamentoController {

    private final FormaPagamentoService service;

    public FormaPagamento converter(FormaPagamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, FormaPagamento.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<FormaPagamento> formasPagamentos = service.getFormasPagamento();
        return ResponseEntity.ok(formasPagamentos.stream().map(FormaPagamentoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter informações de uma forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento encontrada", response = FormaPagamento.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FormaPagamento> formaPagamento = service.getFormaPagamentoById(id);
        if (!formaPagamento.isPresent()) {
            return new ResponseEntity("Forma de pagamento não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(formaPagamento.map(FormaPagamentoDTO::create));
    }

    @ApiOperation("Incluir uma forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento encontrada", response = FormaPagamento.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada")
    })
    @PostMapping()
    public ResponseEntity post(@RequestBody FormaPagamentoDTO dto) {
        try {
            FormaPagamento formaPagamento = converter(dto);
            formaPagamento = service.salvar(formaPagamento);
            return new ResponseEntity(formaPagamento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Atualizar uma forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento encontrada", response = FormaPagamento.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada")
    })
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FormaPagamentoDTO dto) {
        if (!service.getFormaPagamentoById(id).isPresent()) {
            return new ResponseEntity("FormaPagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FormaPagamento formaPagamento = converter(dto);
            formaPagamento.setId(id);
            formaPagamento = service.salvar(formaPagamento);
            return ResponseEntity.ok(formaPagamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Excluir uma forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento encontrada", response = FormaPagamento.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada")
    })
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FormaPagamento> formaPagamento = service.getFormaPagamentoById(id);
        if (!formaPagamento.isPresent()) {
            return new ResponseEntity("FormaPagamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(formaPagamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}