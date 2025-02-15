package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String codigoTransacao;

    public boolean processarPagamento(double valor, String tipoPagamento) {
        if ("CREDITO".equalsIgnoreCase(tipoPagamento) || "DEBITO".equalsIgnoreCase(tipoPagamento)) {
            this.status = "APROVADO";
            this.codigoTransacao = "TX-" + UUID.randomUUID().toString().substring(0, 8);
            return true; // Pagamento aprovado
        } else {
            this.status = "RECUSADO";
            return false; // Pagamento recusado
        }
    }
}