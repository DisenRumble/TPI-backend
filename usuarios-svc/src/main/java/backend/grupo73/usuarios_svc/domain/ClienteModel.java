// backend/grupo73/usuarios_svc/domain/Cliente.java
package backend.grupo73.usuarios_svc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "keycloak_sub", nullable = false, unique = true)
    private String keycloakSub;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String telefono;
    private String direccion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt; // no lo inicializamos acá

    /** Setea created_at si viene null antes del INSERT */
    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    /** Si más adelante agregás updated_at, lo podés setear acá */
    @PreUpdate
    void preUpdate() {
        // updatedAt = Instant.now();
    }
}
