package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalaService {
    private SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public List<Sala> getSalas() {
        return repository.findAll();
    }

    public Optional<Sala> getSalaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Sala salvar(Sala sala) {
        validar(sala);
        return repository.save(sala);
    }

    @Transactional
    public void excluir(Sala sala) {
        Objects.requireNonNull(sala.getId());
        repository.delete(sala);
    }

    public void validar(Sala sala) {
        if (sala.getNumSala() == null || sala.getNumSala().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
        throw new RegraNegocioException("Curso inválido");
        }
    }
}
