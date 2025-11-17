package backend.grupo73.catalogo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                        .requestMatchers("/swagger", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Ubicaciones
                        .requestMatchers(HttpMethod.GET, "/ubicaciones", "/ubicaciones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ubicaciones").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/ubicaciones/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")

                        // Camiones
                        .requestMatchers(HttpMethod.GET, "/camiones", "/camiones/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/camiones").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/camiones/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")

                        // Tarifas
                        .requestMatchers(HttpMethod.GET, "/tarifas", "/tarifas/**").permitAll() // All GET /tarifas endpoints
                        .requestMatchers(HttpMethod.POST, "/tarifas").hasAnyRole("OPERADOR", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/tarifas/**").hasAnyRole("OPERADOR", "ADMINISTRADOR")

                        // Validaciones
                        .requestMatchers(HttpMethod.POST, "/validaciones/camion-capacidad").permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return jwtConverter;
    }

    public static class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

            if (realmAccess == null || realmAccess.isEmpty()) {
                return List.of();
            }

            return ((List<String>) realmAccess.get("roles"))
                    .stream()
                    .map(roleName -> "ROLE_" + roleName.toUpperCase())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }
}
