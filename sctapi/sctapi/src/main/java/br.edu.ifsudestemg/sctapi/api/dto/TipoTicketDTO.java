package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.TipoTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoTicketDTO {
    private Long id;
    private String nome;
    private Float valor;

    public static TipoTicketDTO create(TipoTicketDTO tipoTicket) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tipoTicket, TipoTicketDTO.class);
    }
}
