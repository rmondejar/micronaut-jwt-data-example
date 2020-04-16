package mn.jwt.data.dtos;

import javax.validation.constraints.NotBlank;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Introspected
public class UserDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private String role;

}
