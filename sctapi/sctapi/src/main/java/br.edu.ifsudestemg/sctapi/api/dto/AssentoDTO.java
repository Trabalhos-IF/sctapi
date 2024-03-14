package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Assento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssentoDTO {
    private Long id;
    private String nome;
    private boolean disponivel;

    private Long idTipoAssento;

    public static AssentoDTO create(Assento assento) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(assento, AssentoDTO.class);
}
