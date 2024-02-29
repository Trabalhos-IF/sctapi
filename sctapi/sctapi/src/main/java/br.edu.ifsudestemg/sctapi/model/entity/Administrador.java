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
public class Administrador extends Usuario {


    private String Cinemas;

    //@ManyToMany(mappedBy = "Administradores")
    //private List<Cinemas> cinemasList;
}
