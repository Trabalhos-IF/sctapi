package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promocao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private LocalDate validoAte;
    private double desconto;

    public boolean validarPromocoes(String codigoUsuario) {
        // Verifica se a promoção está dentro do prazo, se o código é válido e se o desconto está entre 0 e 50
        return LocalDate.now().isBefore(this.validoAte) &&
               this.codigo.equalsIgnoreCase(codigoUsuario) &&
               this.desconto > 0 && this.desconto <= 50;
    }
}