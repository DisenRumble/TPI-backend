package backend.grupo73.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String CLIENT_ID = "TPI-backend-client";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/oauth2/**", "/login/**").permitAll()
                        .pathMatchers("/gateway/usuarios/swagger", "/gateway/usuarios/swagger-ui/**", "/gateway/usuarios/v3/api-docs/**").permitAll()
                        .pathMatchers("/gateway/whoami").authenticated()
                        .pathMatchers("/gateway/usuarios/**").hasAnyRole("ADMINISTRADOR", "CLIENTE", "TRANSPORTISTA")
                        .anyExchange().authenticated()
                )
                // Simplemente usamos los valores por defecto. Spring recogerá nuestro bean oidcUserService automáticamente.
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    // Bean para el flujo de Resource Server (cuando se recibe un Bearer Token)
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> claims = jwt.getClaims();
            return Stream.concat(
                    extractRealmRolesFromClaims(claims).stream(),
                    extractClientRolesFromClaims(claims).stream()
            ).collect(Collectors.toList());
        });
        return new ReactiveJwtAuthenticationConverterAdapter(converter);
    }

    // Bean para el flujo de Login (cuando el usuario inicia sesión en el navegador)
    @Bean
    public ReactiveOAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcReactiveOAuth2UserService delegate = new OidcReactiveOAuth2UserService();

        return (userRequest) -> {
            // 1. Dejamos que el servicio por defecto cree el usuario
            return delegate.loadUser(userRequest)
                    .flatMap(oidcUser -> {
                        Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());
                        Map<String, Object> claims = oidcUser.getAttributes();

                        // 2. Extraemos los roles de los claims del token
                        mappedAuthorities.addAll(extractRealmRolesFromClaims(claims));
                        mappedAuthorities.addAll(extractClientRolesFromClaims(claims));

                        // 3. Creamos un nuevo usuario con los permisos combinados
                        return Mono.just(new DefaultOidcUser(
                                mappedAuthorities,
                                oidcUser.getIdToken(),
                                oidcUser.getUserInfo()
                        ));
                    });
        };
    }

    // --- Métodos de ayuda para extraer roles ---

    private List<GrantedAuthority> extractRealmRolesFromClaims(Map<String, Object> claims) {
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess == null) {
            return Collections.emptyList();
        }
        Object roles = realmAccess.get("roles");
        if (roles instanceof Collection<?>) {
            return ((Collection<?>) roles).stream()
                    .map(Object::toString)
                    .map(SecurityConfig::toRole)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<GrantedAuthority> extractClientRolesFromClaims(Map<String, Object> claims) {
        Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
        if (resourceAccess == null) {
            return Collections.emptyList();
        }
        Object clientObj = resourceAccess.get(CLIENT_ID);
        if (clientObj instanceof Map<?, ?>) {
            Object roles = ((Map<?, ?>) clientObj).get("roles");
            if (roles instanceof Collection<?>) {
                return ((Collection<?>) roles).stream()
                        .map(Object::toString)
                        .map(SecurityConfig::toRole)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private static SimpleGrantedAuthority toRole(String raw) {
        return new SimpleGrantedAuthority("ROLE_" + raw.toUpperCase(Locale.ROOT));
    }
}
