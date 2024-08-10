package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Sala;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {
    private Long id;

    private String numSala;
    private String numAssentos;

    private Long idCinema;


    public static SalaDTO create(Sala sala) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sala, SalaDTO.class);
    }
}
