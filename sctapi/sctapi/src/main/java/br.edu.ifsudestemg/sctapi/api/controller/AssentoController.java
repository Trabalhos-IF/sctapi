package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.AssentoDTO;

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

public class AssentoController {
    public Assento converter(AssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Assento.class);
        if (dto.getIdTipoAssento() != null) {
            Optional<TipoAssento> tipoAssento = tipoAssentoService.getTipoAssentoById(dto.getIdTipoAssento());
            if (!tipoAssento.isPresent()) {
                Assento.setTipoAssento(null);
            } else {
                Assento.setDisciplina(tipoAssento.get());
            }
        }
        return assento;
    }

}
