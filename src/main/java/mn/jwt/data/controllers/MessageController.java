package mn.jwt.data.controllers;

import java.util.List;
import io.reactivex.Single;

import io.micronaut.http.HttpResponse;
import io.micronaut.security.annotation.Secured;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import mn.jwt.data.dtos.MessageDto;
import mn.jwt.data.services.MessageService;
import mn.jwt.data.services.UserService;

import static io.micronaut.http.HttpResponseFactory.INSTANCE;
import static io.micronaut.http.HttpStatus.FORBIDDEN;
import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/messages")
@Secured(IS_AUTHENTICATED)
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

    @Post
    @Secured({"ADMIN", "VIEW"})
    public  Single<HttpResponse<MessageDto>> postMessage(@Header String authorization, @Body String content) {

        return Single.just(
                userService.extractAuthUser(authorization).map(user ->
                        messageService.create(content, user.getUsername())
                                .map(message -> HttpResponse.created(message))
                                .orElse(INSTANCE.status(FORBIDDEN))
                )
                .orElse(HttpResponse.unauthorized())
        );
    }
}
