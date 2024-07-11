package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.TipoExibicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoExibicaoService {
    private TipoExibicaoRepository repository;

    public TipoExibicaoService(TipoExibicaoRepository repository) {
        this.repository = repository;
    }

    public List<TipoExibicao> getTiposExibicao() {
        return repository.findAll();
    }

    public Optional<TipoExibicao> getTipoExibicaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TipoExibicao salvar(TipoExibicao tipoExibicao) {
        validar(tipoExibicao);
        return repository.save(tipoExibicao);
    }

    @Transactional
    public void excluir(TipoExibicao tipoExibicao) {
        Objects.requireNonNull(tipoExibicao.getId());
        repository.delete(tipoExibicao);
    }

    public void validar(TipoExibicao tipoExibicao) {
        if (tipoExibicao.getNome() == null || tipoExibicao.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
