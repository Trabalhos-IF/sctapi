package br.edu.ifsudestemg.sctapi.service;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.AssentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AssentoService {
    private AssentoRepository repository;

    public AssentoService(AssentoRepository repository) {
        this.repository = repository;
    }

    public List<Assento> getAssentos() {
        return repository.findAll();
    }

    public Optional<Assento> getAssentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Assento salvar(Assento assento) {
        validar(assento);
        return repository.save(assento);
    }

    @Transactional
    public void excluir(Assento assento) {
        Objects.requireNonNull(assento.getId());
        repository.delete(assento);
    }

    public void validar(Assento assento) {
        if (assento.getNome() == null || assento.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
        throw new RegraNegocioException("Curso inválido");
        }
    }

}


