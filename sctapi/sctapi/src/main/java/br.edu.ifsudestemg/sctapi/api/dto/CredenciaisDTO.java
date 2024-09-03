package br.edu.ifsudestemg.sctapi.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {
    private String senha;
    private String email;

   public String getEmail(){
       return this.email;
   }

   public String getSenha(){
       return this.senha;
   }



}
