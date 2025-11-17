package backend.grupo73.solicitudes_svc.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {
    public static String sub(Authentication a) { return ((Jwt)a.getPrincipal()).getClaimAsString("sub"); }
    public static String email(Authentication a) {
        Jwt j = (Jwt) a.getPrincipal();
        String e = j.getClaimAsString("email");
        return e != null ? e : j.getClaimAsString("preferred_username");
    }
    public static String givenName(Authentication a) { return ((Jwt)a.getPrincipal()).getClaimAsString("given_name"); }
    public static String familyName(Authentication a) { return ((Jwt)a.getPrincipal()).getClaimAsString("family_name"); }
}
