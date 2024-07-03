package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.SessaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessaoService {
    private SessaoRepository repository;

    public SessaoService(SessaoRepository repository) {
        this.repository = repository;
    }

    public List<Sessao> getSessoes() {
        return repository.findAll();
    }

    public Optional<Sessao> getSessaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Sessao salvar(Sessao sessao) {
        validar(sessao);
        return repository.save(sessao);
    }


    public void validar(Sessao sessao) {
//        if (sessao.getId() == null || sessao.getId().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
        //if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
        //throw new RegraNegocioException("Curso inválido");
        //}
    }
 
}
