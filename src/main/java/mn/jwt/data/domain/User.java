package mn.jwt.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {

    public static final String DEFAULT_ROLE = "VIEW";

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Builder.Default
    private String role = DEFAULT_ROLE;

    private String token;

}