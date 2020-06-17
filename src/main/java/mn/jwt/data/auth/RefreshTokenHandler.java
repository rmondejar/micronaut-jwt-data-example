package mn.jwt.data.auth;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import io.reactivex.Flowable;
import mn.jwt.data.exceptions.TokenNotFoundException;
import org.reactivestreams.Publisher;

import mn.jwt.data.services.UserService;

import javax.inject.Singleton;

import static java.util.Collections.singletonList;

@Singleton
public class RefreshTokenHandler implements RefreshTokenPersistence {

    private final UserService userService;

    public RefreshTokenHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    @EventListener
    public void persistToken(RefreshTokenGeneratedEvent event) {
        userService.saveRefreshToken(event.getUserDetails().getUsername(), event.getRefreshToken());
    }

    @Override
    public Publisher<UserDetails> getUserDetails(String refreshToken) {
        return userService.findByRefreshToken(refreshToken)
                        .map(user -> Flowable.just(new UserDetails(user.getUsername(), singletonList(user.getRole()))))
                        .orElse(Flowable.error(TokenNotFoundException::new));
    }
}
