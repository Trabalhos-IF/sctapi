package br.edu.ifsudestemg.sctapi.config;

import br.edu.ifsudestemg.sctapi.security.JwtAuthFilter;
import br.edu.ifsudestemg.sctapi.security.JwtService;
import br.edu.ifsudestemg.sctapi.service.AdministradorService;
import br.edu.ifsudestemg.sctapi.service.ClienteService;
import br.edu.ifsudestemg.sctapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ClienteService clienteService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService, administradorService, clienteService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/administradores/**").hasRole("ADMIN")
                .antMatchers("/api/clientes/**").hasRole("ADMIN")
                .antMatchers("/api/categorias/**").hasRole("ADMIN")
                .antMatchers("/api/cinemas/**").permitAll()
                .antMatchers("/api/compras/**").authenticated()
                .antMatchers("/api/filmes/**").permitAll()
                .antMatchers("/api/formaspagamento/**").authenticated()
                .antMatchers("/api/produtoras/**").hasRole("ADMIN")
                .antMatchers("/api/salas/**").hasRole("ADMIN")
                .antMatchers("/api/sessoes/**").permitAll()
                .antMatchers("/api/tipoassentos/**").hasRole("ADMIN")
                .antMatchers("/api/tipoexibicoes/**").hasRole("ADMIN")
                .antMatchers("/api/tipotickets/**").hasRole("ADMIN")
                .antMatchers("/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
