package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.ClienteDTO;

import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.model.entity.Cliente;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor

public class ClienteController{

    private final ClienteService service;

    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cliente.class);
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = service.getClienteById(id);
        if (!cliente.isPresent()) {
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }




}