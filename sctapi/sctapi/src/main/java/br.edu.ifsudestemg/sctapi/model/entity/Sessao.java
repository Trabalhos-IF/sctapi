package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dtExibicao ;
   private LocalTime horarioInicial;
    //private float reservaAssentosMeia;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private Filme filme;

    @ManyToOne
    private Cinema cinema;

    @ManyToOne
    private TipoExibicao tipoExibicao;


}
