package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Sessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.modelmapper.ModelMapper;
//import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {
    private Long id;

    //private Date dtExibicao;
    //private Date horarioInic;
    //private float reservaAssentosMeia;

    private Long idSala;

    private Long idFilme;

    private Long idCinema;

    private Long idTipoExibicao;

    private Long idTipoTicket;

}
