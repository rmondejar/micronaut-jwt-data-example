package mn.jwt.data.controllers;

import io.reactivex.Single;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import mn.jwt.data.dtos.MessageDto;
import mn.jwt.data.services.MessageService;
import mn.jwt.data.services.UserService;

import java.util.List;

@Controller("/messages")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Get("/all")
    @Secured("ADMIN")
    public Single<List<MessageDto>> getAllMessages() {

        return Single.just(messageService.findAll());
    }

    @Get
    @Secured({"ADMIN", "VIEW"})
    public  Single<HttpResponse<List<MessageDto>>> getMessages(@Header String authorization) {

        return Single.just(
                userService.extractAuthUser(authorization).map(user ->
                        HttpResponse.ok(messageService.findAllByUsername(user.getUsername()))
                    )
                    .orElse(HttpResponse.unauthorized())
        );
    }
}
