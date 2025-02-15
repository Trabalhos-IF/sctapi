package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dtExibicao;
    private LocalTime horarioInicial;

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private Filme filme;

    @ManyToOne
    private Cinema cinema;

    @ManyToOne
    private TipoExibicao tipoExibicao;

    public boolean verificarHorario() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioSessao = LocalDateTime.of(this.dtExibicao, this.horarioInicial);

        // Verifica se o horário da sessão é pelo menos 30 minutos no futuro
        return horarioSessao.isAfter(agora.plusMinutes(30));
    }
}