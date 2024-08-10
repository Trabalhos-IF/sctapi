package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Compra{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String email;
    private int quantidade;
    private float valor;

    @ManyToOne
    private FormaPagamento formaPagamento;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Sessao sessao;

}
