package br.edu.ifsudestemg.sctapi.service;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmeService {
     private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public List<Filme> getFilmes() {
        return repository.findAll();
    }

    public Optional<Filme> getFilmeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Filme salvar(Filme filme) {
        validar(filme);
        return repository.save(filme);
    }

    @Transactional
    public void excluir(Filme filme) {
        Objects.requireNonNull(filme.getId());
        repository.delete(filme);
    }
    public void validar(Filme filme) {
        if (filme.getTitulo() == null || filme.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Nome do filme inválido");
        }
        if (filme.getSinopse() == null || filme.getSinopse().trim().equals("")) {
            throw new RegraNegocioException("Sinopse do filme inválida");
        }
//        if (filme.getFaixaEtaria() == null || filme.getFaixaEtaria().trim().equals("")) {
//            throw new RegraNegocioException("Faixa etária inválida");
//        }
        if (filme.getProdutora() == null ) {
            throw new RegraNegocioException("Produtora inválida");
        }
        if (filme.getCategoria() == null ) {
            throw new RegraNegocioException("Categoria inválida");
        }
    }
   
}
