package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AdministradorDTO;
import br.edu.ifsudestemg.sctapi.api.dto.AssentoDTO;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.api.dto.TipoAssentoDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TipoTicketDTO;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import br.edu.ifsudestemg.sctapi.service.AssentoService;
import br.edu.ifsudestemg.sctapi.service.TipoAssentoService;

import br.edu.ifsudestemg.sctapi.model.entity.Assento;
import br.edu.ifsudestemg.sctapi.model.entity.TipoAssento;
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
@RequestMapping("/api/v1/assentos")
@RequiredArgsConstructor
@CrossOrigin
public class AssentoController {
    private final TipoAssentoService tipoAssentoService;
    private final AssentoService service;

    public Assento converter(AssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Assento assento = modelMapper.map(dto, Assento.class);

        if (dto.getIdTipoAssento() != null) {
            Optional<TipoAssento> tipoAssento = tipoAssentoService.getTipoAssentoById(dto.getIdTipoAssento());
            if (!tipoAssento.isPresent()) {
                assento.setTipoAssento(null); // Use the assento object
            } else {
                assento.setTipoAssento(tipoAssento.get()); // Use the assento object
            }
        }
        return assento;
    }

    @GetMapping()
    public ResponseEntity get() {
        List<Assento> assentos = service.getAssentos();
        return ResponseEntity.ok(assentos.stream().map(AssentoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de um assento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "assento encontrado", response = Assento.class),
            @ApiResponse(code = 404, message = "assento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.getAssentoById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Tipo de Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assento.map(AssentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AssentoDTO dto) {
        try {
            Assento assento = converter(dto);
            assento = service.salvar(assento);
            return new ResponseEntity(assento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AssentoDTO dto) {
        if (!service.getAssentoById(id).isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Assento assento = converter(dto);
            assento.setId(id);
            assento = service.salvar(assento);
            return ResponseEntity.ok(assento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.getAssentoById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(assento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
