package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AssentoDTO;

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

@RestController
@RequestMapping("/api/v1/assento")
@RequiredArgsConstructor
public class AssentoController {
    private final TipoAssentoService tipoAssentoService;

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

}
