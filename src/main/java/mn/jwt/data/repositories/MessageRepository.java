package mn.jwt.data.repositories;

import java.util.List;
import java.util.UUID;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;

import mn.jwt.data.domain.Message;
import mn.jwt.data.domain.User;

@Repository
public interface MessageRepository extends PageableRepository<Message, UUID> {

    List<Message> findAllByUser(User user);
}

