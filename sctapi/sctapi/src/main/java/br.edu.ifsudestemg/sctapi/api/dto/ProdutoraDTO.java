package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Produtora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoraDTO {
    private Long id;

    private String nome;

    private Long idFilme;

    public static ProdutoraDTO create(Produtora Produtora) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(Produtora, ProdutoraDTO.class);
    }
}
