package backend.grupo73.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WhoAmIController {

    private final ServerOAuth2AuthorizedClientRepository authorizedClientRepository;

    @GetMapping("/gateway/whoami")
    public Mono<Map<String, Object>> whoami(ServerWebExchange exchange) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> {
                    Map<String, Object> info = toInfo(auth);
                    if (auth instanceof OAuth2AuthenticationToken token) {
                        return authorizedClientRepository.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), auth, exchange)
                                .map(client -> {
                                    info.put("access_token", client.getAccessToken().getTokenValue());
                                    return info;
                                })
                                .defaultIfEmpty(info);
                    }
                    return Mono.just(info);
                });
    }

    private static Map<String, Object> toInfo(Authentication auth) {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("authenticated", auth.isAuthenticated());
        // Simplificamos la salida de las authorities para que sea una lista de strings
        out.put("authorities", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        Object p = auth.getPrincipal();

        if (p instanceof OidcUser u) {
            out.put("type", "OIDC");
            out.put("sub", u.getSubject());
            out.put("name", u.getFullName());
            out.put("preferred_username", u.getPreferredUsername());
            out.put("email", u.getEmail());
            out.put("id_token", u.getIdToken().getTokenValue()); // También mostramos el ID Token
            return out;
        }

        if (p instanceof Jwt jwt) {
            out.put("type", "JWT");
            out.put("sub", jwt.getSubject());
            out.put("issuer", String.valueOf(jwt.getIssuer()));
            out.put("expires_at", String.valueOf(jwt.getExpiresAt()));
            out.put("access_token", jwt.getTokenValue()); // Si es un JWT, el token es el access token
            return out;
        }

        out.put("type", p == null ? "null" : p.getClass().getName());
        out.put("principal", String.valueOf(p));
        return out;
    }
}
