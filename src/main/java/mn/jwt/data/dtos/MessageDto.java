package mn.jwt.data.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.micronaut.core.annotation.Introspected;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Introspected
public class MessageDto {

    @NotNull
    private Long id;
    @NotBlank
    private String content;
    @NotNull
    private LocalDate creationDate;
    @NotNull
    private UserDto user;

}
