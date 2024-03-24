package br.edu.ifsudestemg.sctapi.service;

//import com.example.scaapi.exception.RegraNegocioException;
import br.edu.ifsudestemg.sctapi.model.entity.*;
import br.edu.ifsudestemg.sctapi.model.repository.TipoTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoTicketService {
    private TipoTicketRepository repository;

    public TipoTicketService(TipoTicketRepository repository) {
        this.repository = repository;
    }

    public List<TipoTicket> getTiposTicket() {
        return repository.findAll();
    }

    public Optional<TipoTicket> getTipoTicketById(Long id) {
        return repository.findById(id);
    }

}
