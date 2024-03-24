package br.edu.ifsudestemg.sctapi.api.service;

//import com.example.scaapi.exception.RegraNegocioException;
import com.example.scaapi.model.entity.*;
import com.example.scaapi.model.repository.CinemaRepository;
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


}
