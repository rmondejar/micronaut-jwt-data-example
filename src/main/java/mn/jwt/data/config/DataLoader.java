package mn.jwt.data.config;

import java.util.Arrays;
import javax.inject.Singleton;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import lombok.extern.slf4j.Slf4j;

import mn.jwt.data.domain.User;
import mn.jwt.data.repositories.UserRepository;

@Slf4j
@Singleton
public class DataLoader implements ApplicationEventListener<ServerStartupEvent> {

    final UserRepository usersRepository;

    public DataLoader(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        log.info("Demo data added");

        usersRepository.saveAll(Arrays.asList(
                User.builder().username("user1").password("password1").build(),
                User.builder().username("user2").password("password2").build(),
                User.builder().username("user3").password("password3").build()
        ));
    }
}