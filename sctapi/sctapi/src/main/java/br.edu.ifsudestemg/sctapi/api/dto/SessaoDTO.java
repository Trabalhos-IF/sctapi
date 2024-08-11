package br.edu.ifsudestemg.sctapi.api.dto;

import br.edu.ifsudestemg.sctapi.model.entity.Sessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDTO {

    private Long id;

    private LocalDate dtExibicao;
    private LocalTime horarioInicial;
    //private float reservaAssentosMeia;

    private Long idSala;

    private Long idFilme;

    private Long idCinema;

    private Long idTipoExibicao;


    public static SessaoDTO create(Sessao sessao) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sessao, SessaoDTO.class);

    }
}
