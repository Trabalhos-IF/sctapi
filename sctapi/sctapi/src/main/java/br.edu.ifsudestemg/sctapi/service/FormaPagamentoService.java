package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;;

@Service
public class FormaPagamentoService {
    private FormaPagamentoRepository repository;

    public FormaPagamentoService(FormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public List<FormaPagamento> getFormasPagamento() {
        return repository.findAll();
    }

    public Optional<FormaPagamento> getFormaPagamentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        validar(formaPagamento);
        return repository.save(formaPagamento);
    }

    public void validar(FormaPagamento formaPagamento) {
        if (formaPagamento.getId() == null || formaPagamento.getId().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        //if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
        //throw new RegraNegocioException("Curso inválido");
        //}
    }

}