package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.TipoAssento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAssentoDTO {
    private Long id;

    private String nome;

    public static TipoAssentoDTO create(TipoAssento tipoAssento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tipoAssento, TipoAssentoDTO.class);
}
