package br.edu.ifsudestemg.sctapi.service;

//import com.example.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.AdministradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdministradorService{

    private AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    public List<Administrador> getAdministradores() {
        return repository.findAll();
    }

    public Optional<Administrador> getAdministradorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Administrador salvar(Administrador administrador) {
        validar(administrador);
        return repository.save(administrador);
    }

    @Transactional
    public void excluir(Administrador administrador) {
        Objects.requireNonNull(administrador.getId());
        repository.delete(administrador);
    }

    public void validar(Administrador administrador) {
        if (administrador.getNome() == null || administrador.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (administrador.getCurso() == null || administrador.getCurso().getId() == null || administrador.getCurso().getId() == 0) {
        throw new RegraNegocioException("Curso inválido");
        }
    }
}
