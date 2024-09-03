package br.edu.ifsudestemg.sctapi.service;

import br.edu.ifsudestemg.sctapi.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.sctapi.model.entity.Administrador;
import br.edu.ifsudestemg.sctapi.model.entity.Cliente;
import br.edu.ifsudestemg.sctapi.model.entity.Usuario;
import br.edu.ifsudestemg.sctapi.model.repository.AdministradorRepository;
import br.edu.ifsudestemg.sctapi.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AdministradorRepository administradorRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        if(usuario instanceof Cliente)
            return clienteRepository.save((Cliente) usuario);
        else
            return administradorRepository.save((Administrador) usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getEmail());
        System.out.println(usuario.getSenha());
        System.out.println(user.getPassword());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario;
        Cliente optionalCliente = clienteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));;

//        try {
//           Cliente optionalCliente = clienteRepository.findByEmail(username);
//            System.out.println(optionalCliente.isPresent());
//            if (optionalCliente.isPresent()) {
//                usuario = optionalCliente.get();
//                System.out.println("a");
//            } else {
//                throw new UsernameNotFoundException("Usuário não encontrado");
//            }
//
//        } catch( Exception e){
//            Optional<Administrador> optionalAdministrador =
//                    administradorRepository.findByEmail(username);
//            if (optionalAdministrador.isPresent()) {
//                usuario = optionalAdministrador.get();
//            } else {
//                throw new UsernameNotFoundException("Usuário não encontrado");
//            }
//        }

        String[] roles = optionalCliente.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(optionalCliente.getEmail())
                .password(optionalCliente.getSenha())
                .roles(roles)
                .build();
    }
}