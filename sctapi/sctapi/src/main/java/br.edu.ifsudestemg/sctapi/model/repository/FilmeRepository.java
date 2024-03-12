package br.edu.ifsudestemg.sctapi.model.repository;

import br.edu.ifsudestemg.sctapi.model.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Long> {

}