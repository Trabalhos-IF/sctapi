package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.TipoAssentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoAssentoService {
    private TipoAssentoRepository repository;

    public TipoAssentoService(TipoAssentoRepository repository) {
        this.repository = repository;
    }

    public List<TipoAssento> getTiposAssento() {
        return repository.findAll();
    }

    public Optional<TipoAssento> getTipoAssentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoAssento salvar(TipoAssento tipoAssento) {
        validar(tipoAssento);
        return repository.save(tipoAssento);
    }

    @Transactional
    public void excluir(TipoAssento tipoAssento) {
        Objects.requireNonNull(tipoAssento.getId());
        repository.delete(tipoAssento);
    }

    public void validar(TipoAssento tipoAssento) {
        if (tipoAssento.getNome() == null || tipoAssento.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
        throw new RegraNegocioException("Curso inválido");
        }
    }
   

}
