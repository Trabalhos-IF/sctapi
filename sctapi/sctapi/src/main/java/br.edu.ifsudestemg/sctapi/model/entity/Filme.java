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
public class Filme{

    private String titulo;
    private String sinopse;
    private String faixaEtaria;
    private String duracao;

    //@ManyToMany

}
