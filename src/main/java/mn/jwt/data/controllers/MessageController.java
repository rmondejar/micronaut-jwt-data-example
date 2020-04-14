package mn.jwt.data.controllers;

import io.reactivex.Single;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import mn.jwt.data.dtos.MessageDto;
import mn.jwt.data.services.MessageService;

import java.util.List;

@Controller("/messages")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Get("/all")
    @Secured("ADMIN")
    public Single<List<MessageDto>> getAllMessages() {

        return Single.just(messageService.findAll());
    }

    @Get
    @Secured({"ADMIN", "VIEW"})
    public  Single<HttpResponse<List<MessageDto>>> getMessages(@Header String authorization) {

        String username;

        try {
            SignedJWT jwt = (SignedJWT) JWTParser.parse(authorization.substring(7));
            username = jwt.getJWTClaimsSet().getSubject();

        } catch (Exception e) {
            return Single.just(HttpResponse.unauthorized());
        }

        return Single.just(HttpResponse.ok(messageService.findAllByUsername(username)));
    }

}
