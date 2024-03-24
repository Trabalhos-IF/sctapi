package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.ProdutoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;;

@Service
public class ProdutoraService {
    private ProdutoraRepository repository;

    public ProdutoraService(ProdutoraRepository repository) {
        this.repository = repository;
    }

    public List<Produtora> getProdutoras() {
        return repository.findAll();
    }

    public Optional<Produtora> getProdutoraById(Long id) {
        return repository.findById(id);
    }
}
