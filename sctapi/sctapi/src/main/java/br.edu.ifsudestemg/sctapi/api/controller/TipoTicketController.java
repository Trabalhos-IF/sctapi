package br.edu.ifsudestemg.sctapi.api.controller;


import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.ProdutoraDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Produtora;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.TipoTicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tiposTickets")
@RequiredArgsConstructor
@CrossOrigin
public class TipoTicketController {

    private final TipoTicketService service;

    public TipoTicket converter(TipoTicketDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoTicket.class);
    }

    //@GetMapping()
    //public ResponseEntity get() {
       // List<TipoTicket> tipoTickets = service.getTiposTicket();
        //return ResponseEntity.ok(tipoTickets.stream().map(TipoTicketDTO::create).collect(Collectors.toList()));
    //}

    //@GetMapping("/{id}")
    //public ResponseEntity get(@PathVariable("id") Long id) {
        //Optional<TipoTicket> tipoTicket = service.getTipoTicketById(id);
        //if (!tipoTicket.isPresent()) {
            //return new ResponseEntity("Tipo de Ticket não encontrado", HttpStatus.NOT_FOUND);
        //}
        //return ResponseEntity.ok(tipoTicket.map(TipoTicketDTO::create));
    //}


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoTicketDTO dto) {
        if (!service.getTipoTicketById(id).isPresent()) {
            return new ResponseEntity("TipoTicket não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoTicket tipoTicket = converter(dto);
            tipoTicket.setId(id);
            tipoTicket = service.salvar(tipoTicket);
            return ResponseEntity.ok(tipoTicket);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoTicket> tipoTicket = service.getTipoTicketById(id);
        if (!tipoTicket.isPresent()) {
            return new ResponseEntity("TipoTicket não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipoTicket.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
