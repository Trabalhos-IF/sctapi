package br.edu.ifsudestemg.sctapi.model.repository;

import br.edu.ifsudestemg.sctapi.model.entity.TipoExibicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoExibicaoRepository extends JpaRepository<TipoExibicao, Long> {

}