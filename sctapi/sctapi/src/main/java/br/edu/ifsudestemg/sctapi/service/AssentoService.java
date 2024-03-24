package br.edu.ifsudestemg.sctapi.api.service;

//import com.example.scaapi.exception.RegraNegocioException;
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

}


