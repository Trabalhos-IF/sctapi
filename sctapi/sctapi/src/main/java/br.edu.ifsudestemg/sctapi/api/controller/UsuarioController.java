package br.edu.ifsudestemg.sctapi.api.controller;

import br.edu.ifsudestemg.sctapi.api.dto.CredenciaisDTO;
import br.edu.ifsudestemg.sctapi.api.dto.TokenDTO;
import br.edu.ifsudestemg.sctapi.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.sctapi.model.entity.Cliente;
import br.edu.ifsudestemg.sctapi.model.entity.Usuario;
import br.edu.ifsudestemg.sctapi.security.JwtService;
import br.edu.ifsudestemg.sctapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Cliente cliente) {
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
        return usuarioService.salvar(cliente);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {
            Cliente cliente = Cliente.builder()
                    .email(credenciais.getEmail())
                    .senha(credenciais.getSenha())
                   .build();

            UserDetails usuarioAutenticado = usuarioService.autenticar(cliente);
            String token = jwtService.gerarToken( cliente);
            return new TokenDTO(cliente.getEmail(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
