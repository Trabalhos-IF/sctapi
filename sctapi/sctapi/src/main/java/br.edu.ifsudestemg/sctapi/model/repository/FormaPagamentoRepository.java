package br.edu.ifsudestemg.sctapi.model.repository;

import br.edu.ifsudestemg.sctapi.model.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}