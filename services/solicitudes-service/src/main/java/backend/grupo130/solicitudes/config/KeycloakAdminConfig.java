package backend.grupo130.solicitudes.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(KeycloakAdminProperties.class)
public class KeycloakAdminConfig {

    @Bean
    public RestClient keycloakRestClient(RestClient.Builder builder, KeycloakAdminProperties properties) {
        return builder
            .baseUrl(properties.serverUrl())
            .build();
    }
}
