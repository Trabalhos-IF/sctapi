package br.edu.ifsudestemg.sctapi.service;

import br.edu.ifsudestemg.sctapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.CinemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CinemaService {
     private CinemaRepository repository;

    public CinemaService(CinemaRepository repository) {
        this.repository = repository;
    }

    public List<Cinema> getCinemas() {
        return repository.findAll();
    }

    public Optional<Cinema> getCinemaById(Long id) {
        return repository.findById(id);
    }


    @Transactional
    public Cinema salvar(Cinema cinema) {
        validar(cinema);
        return repository.save(cinema);
    }


    public void validar(Cinema cinema) {
        if (cinema.getNome() == null || cinema.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        //if (cinema.getCurso() == null || cinema.getCurso().getId() == null || cinema.getCurso().getId() == 0) {
            //throw new RegraNegocioException("Curso inválido");
        //}
    }


}
