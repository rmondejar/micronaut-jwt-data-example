package mn.jwt.data;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.micronaut.runtime.Micronaut;

@OpenAPIDefinition(
        info = @Info(
                title = "mn-jwt-jpa",
                version = "0.3"
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}