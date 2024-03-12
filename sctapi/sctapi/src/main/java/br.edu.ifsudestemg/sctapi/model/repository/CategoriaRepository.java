package br.edu.ifsudestemg.sctapi.model.repository;

import br.edu.ifsudestemg.sctapi.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}