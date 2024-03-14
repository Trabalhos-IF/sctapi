package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    private Long id;
    private Long idFormaDePagamento;
    private Long idCliente;
    private Long idSessao;

    public static CompraDTO create(Compra compra) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(compra, CompraDTO.class);
}
