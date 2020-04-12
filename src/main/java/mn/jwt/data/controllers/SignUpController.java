package mn.jwt.data.controllers;

import java.util.Optional;
import io.reactivex.Single;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import mn.jwt.data.dto.UserDto;
import mn.jwt.data.services.UserService;


@Controller("/signup")
@Secured(SecurityRule.IS_ANONYMOUS)
public class SignUpController {

    final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @Post
    public Single<HttpResponse<UserDto>> registerUser(UserDto userDto) {

        Optional<UserDto> existingUser = userService.findUser(userDto.getUsername());

        return Single.just(existingUser
                .map(HttpResponse::badRequest)
                .orElse(HttpResponse.ok(userService.createUser(userDto)))
        );
    }

}
