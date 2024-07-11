package com.project.cnh_manager.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(authorize -> authorize.
            requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() 
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/auth/user/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/auth/user/all/instrutor").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/auth/setinstrutor/{instrutorId}/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/solicitacoes/all").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/solicitacoes/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/solicitacoes/cadastrar").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/solicitacoes/all/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "provas/all/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/solicitacaoDeProva/cadastrar").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/solicitacaoDeProva/all").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/solicitacaoDeProva/all/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/solicitacaoDeProva/delete/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/provas/cadastrar/{solicitacaoId}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/pagamentos/all").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/pagamentos/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/solicitacaoDeProva/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/provas/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/pagamentos/user/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/pagamentos/cadastrar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/horario/cadastrar").hasRole("INSTRUTOR")
                    .requestMatchers(HttpMethod.GET, "/horario/all").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/horario/all/user/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/horario/all/{InstrutorId}").hasRole("INSTRUTOR")
                    .requestMatchers(HttpMethod.GET, "/horario/awatingapproval/{instrutorId}").hasRole("INSTRUTOR")
                    .requestMatchers(HttpMethod.PUT, "/horario/{id}/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/horario/confirm/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/horario/confirmed/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/horario/approveOrDeny/{horarioId}").hasRole("INSTRUTOR")
                    .requestMatchers(HttpMethod.PUT, "/horario/{id}/{userId}").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/horario/cancel/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/horario/delete/{id}").hasRole("INSTRUTOR")
                    .requestMatchers(HttpMethod.GET, "/carga-horaria/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/indicators/avaliacoes/all").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/indicators/avaliacoes/average").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/indicators/avaliacoes/all/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/indicators/avaliacoes/{id}").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/indicators/avaliacoes/cadastrar").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/indicators/calculateApprovalRate").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/indicators/calculateAverageAdditionalPractice").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/provas/inconcluidas").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/provas/aprovarAluno/{id}").hasRole("ADMIN")
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

