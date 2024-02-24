package br.edu.ifsudestemg.sctapi.model.entity

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cinema{

    private String nome;
    private String cnpj;
    private String email;
    private String numSalas;
    private String telefone;

    //@ManyToMany

}
