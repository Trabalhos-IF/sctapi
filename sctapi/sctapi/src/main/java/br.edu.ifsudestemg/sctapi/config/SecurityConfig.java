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
                // Endpoints relacionados à administração, acessíveis apenas por administradores
                .antMatchers("/api/administradores/**",
                        "/api/clientes/**",
                        "/api/categorias/**",
                        "/api/produtoras/**",
                        "/api/salas/**",
                        "/api/tipoassentos/**",
                        "/api/tipoexibicoes/**",
                        "/api/tipotickets/**",
                        "/api/usuarios/**").hasRole("ADMIN")

                // Endpoints relacionados a compras e formas de pagamento, acessíveis por usuários autenticados
                .antMatchers("/api/compras/**",
                        "/api/formaspagamento/**").authenticated()

                // Endpoints públicos, acessíveis por todos
                .antMatchers("/api/cinemas/**",
                        "/api/filmes/**",
                        "/api/sessoes/**").permitAll()

                // Endpoints do Front-end React
                .antMatchers("/views/cadastro/**",
                        "/views/listagem/**").hasRole("ADMIN")  // Apenas administradores podem acessar as páginas de cadastro e listagem
                .antMatchers("/views/usuarios/comprar").hasRole("USER")  // Apenas clientes logados podem acessar a página de compra
                .antMatchers("/views/usuarios/**").authenticated() // Usuários autenticados podem acessar qualquer página em `/usuarios`

                // Qualquer outra requisição precisa estar autenticada
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
