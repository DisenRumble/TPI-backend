package backend.grupo73.catalogo.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositoRes {
    private String id;
    private String name;
    private String address;
}
