package br.edu.ifsudestemg.sctapi.service;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.repository.AdministradorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdministradorService {

    private final AdministradorRepository repository;

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
        if (administrador.getCpf() == null || administrador.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (administrador.getEmail() == null || administrador.getEmail().trim().equals("")) {
            throw new RegraNegocioException("Email inválido");
        }
        if (administrador.getCinemas() == null || administrador.getCinemas().trim().equals("")) {
            throw new RegraNegocioException("Cinema inválido");
        }
    }

}
