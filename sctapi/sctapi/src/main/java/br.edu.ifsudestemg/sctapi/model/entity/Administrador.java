package br.edu.ifsudestemg.sctapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
//@NoArgsConstructor
//@AllArgsConstructor
public class Administrador extends Usuario {


    private String Cinemas;

    //@ManyToOne
}
