package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.CompraDTO;

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

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor

public class CompraController {
    private final CompraService service;
    private final FormaPagamentoService formaPagamentoService;
    private final ClienteService clienteService;
    private final SessaoService sessaoService;
    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);

        if (dto.getIdFormaPagamento() != null) {
            Optional <FormaPagamento> formaPagamento = formaPagamentoService.getFormaPagamentoById(dto.getIdFormaPagamento());

            formaPagamento.ifPresent(compra::setFormaPagamento);
//            if (!formaPagamento.isPresent()) {
//                compra.setFormaPagamento(null);
//            } else {
//                compra.setFormaPagamento(formaPagamento.get());
//            }
        }

        if (dto.getIdCliente() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getIdCliente());

            if (!cliente.isPresent()) {
                compra.setCliente(null);
            } else {
                compra.setCliente(cliente.get());
            }
        }
        if (dto.getIdSessao() != null) {
            Optional<Sessao> sessao = sessaoService.getSessaoById(dto.getIdSessao());

            if (!sessao.isPresent()) {
                compra.setSessao(null);
            } else {
                compra.setSessao(sessao.get());
            }
        }

        return compra;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Compra> compras = service.getCompras();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody CompraDTO dto) {
        try {
            Compra compra = converter(dto);
            compra = service.salvar(compra);
            return new ResponseEntity(compra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CompraDTO dto) {
        if (!service.getCompraById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Compra compra = converter(dto);
            compra.setId(id);
            compra = service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

