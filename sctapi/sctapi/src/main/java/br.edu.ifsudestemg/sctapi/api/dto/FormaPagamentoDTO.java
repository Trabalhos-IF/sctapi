package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoDTO {
    private Long id;

    private Long idFormaPagamento;
}
