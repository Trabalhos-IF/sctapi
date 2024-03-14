package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.TipoExibicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoExibicaoDTO {
    private Long id;

    private String nome;

    public static TipoExibicaoDTO create(TipoExibicao tipoExibicao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tipoExibicao, TipoExibicaoDTO.class);
}
