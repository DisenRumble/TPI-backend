package backend.grupo73.catalogo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/depositos", "/camiones", "/tarifas").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/depositos/**", "/camiones/**", "/tarifas/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/depositos", "/camiones").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/tarifas/vigente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/validaciones/camion-capacidad").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
