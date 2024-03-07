package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class categoriaDTO {
    private Long id;

    private String Nome;

    private Long idFilme;
}
