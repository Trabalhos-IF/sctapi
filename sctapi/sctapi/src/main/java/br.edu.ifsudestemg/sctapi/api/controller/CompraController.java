package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.CompraDTO;

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
}
