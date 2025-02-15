package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
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

    public boolean validarEstoque(int quantidadeRequerida) {
        if (sessao == null || sessao.getSala() == null) {
            return false; // Sessão ou sala inválida
        }

        // Conta quantos assentos estão disponíveis na sala da sessão
        int assentosDisponiveis = sessao.getSala().getAssentos().stream()
            .filter(Assento::isDisponivel)
            .collect(Collectors.toList())
            .size();

        return assentosDisponiveis >= quantidadeRequerida; // Retorna true se houver assentos suficientes
    }
}