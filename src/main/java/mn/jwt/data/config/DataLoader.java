package mn.jwt.data.config;

import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;

import lombok.extern.slf4j.Slf4j;

import mn.jwt.data.domain.Message;
import mn.jwt.data.domain.User;
import mn.jwt.data.repositories.MessageRepository;
import mn.jwt.data.repositories.UserRepository;

@Slf4j
@Singleton
public class DataLoader implements ApplicationEventListener<ServerStartupEvent> {

    private final UserRepository usersRepository;
    private final MessageRepository messageRepository;

    public DataLoader(UserRepository usersRepository, MessageRepository messageRepository) {
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {


        List<User> users = Arrays.asList(
                User.builder().username("user1").password("password1").build(),
                User.builder().username("user2").password("password2").build(),
                User.builder().username("user3").password("password3").build()
        );
        usersRepository.saveAll(users);

        List<Message> messages =  Arrays.asList(
                Message.builder().content("My name is user1").user(users.get(0)).build(),
                Message.builder().content("My name is user2").user(users.get(1)).build(),
                Message.builder().content("My name is user3").user(users.get(2)).build()
        );
        messageRepository.saveAll(messages);

        log.info("Demo data added : "+users.size()+" users and "+messages.size()+" messages");
    }
}