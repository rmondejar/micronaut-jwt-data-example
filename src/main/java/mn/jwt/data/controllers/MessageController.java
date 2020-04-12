package mn.jwt.data.controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/messages")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MessageController {

    @Get("/all")
    @Secured({"ADMIN"})
    public String getAllMessages() {
        return "Not Implemented Yet";
    }

    @Get
    @Secured({"VIEW"})
    public String getMyMessages() {
        return "Not Implemented Yet";
    }

}
