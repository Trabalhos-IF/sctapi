package br.edu.ifsudestemg.sctapi.security;

import br.edu.ifsudestemg.sctapi.service.UsuarioService;
import br.edu.ifsudestemg.sctapi.service.AdministradorService;
import br.edu.ifsudestemg.sctapi.service.ClienteService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UsuarioService usuarioService;
    private AdministradorService administradorService;
    private ClienteService clienteService;

    public JwtAuthFilter(JwtService jwtService, UsuarioService usuarioService, AdministradorService administradorService,
                         ClienteService clienteService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.administradorService = administradorService;
        this.clienteService = clienteService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);

            if (isValid) {
                String loginUsuario = jwtService.obterLoginUsuario(token);

                try {
                    UserDetails administrador = administradorService.loadUserByUsername(loginUsuario);
                    UsernamePasswordAuthenticationToken adm = new UsernamePasswordAuthenticationToken(
                            administrador, null, administrador.getAuthorities());
                    adm.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(adm);
                } catch (UsernameNotFoundException e) {
                    try {
                        UserDetails cliente = clienteService.loadUserByUsername(loginUsuario);
                        UsernamePasswordAuthenticationToken clie = new UsernamePasswordAuthenticationToken(
                                cliente, null, cliente.getAuthorities());
                        clie.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(clie);
                    } catch (UsernameNotFoundException ex) {
                        UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
                        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                                usuario, null, usuario.getAuthorities());
                        user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(user);
                    }
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
