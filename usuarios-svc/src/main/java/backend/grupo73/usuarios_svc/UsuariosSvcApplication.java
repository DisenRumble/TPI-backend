package backend.grupo73.usuarios_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class UsuariosSvcApplication {
    public static void main(String[] args) {
        // fuerza un nombre IANA válido antes de que el driver de PG se conecte
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.setProperty("user.timezone", "UTC");
        SpringApplication.run(UsuariosSvcApplication.class, args);
    }
}
