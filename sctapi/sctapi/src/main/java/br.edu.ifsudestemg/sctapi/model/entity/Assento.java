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
public class Assento{

    private String nome;
    private boolean disponivel;

    @ManyToMany(mappedBy = "Sala")
    private List<Salas> salasList;
}
