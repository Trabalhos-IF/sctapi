package br.edu.ifsudestemg.sctapi.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {
    private String nome;
    private String senha;

    // Adicione aqui, caso o campo email seja realmente necessário.
    private String email;

    public String getEmail() {
        return this.email;
    }
}
