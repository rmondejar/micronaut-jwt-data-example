package mn.jwt.data.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Singleton;

import mn.jwt.data.domain.Message;
import mn.jwt.data.dtos.MessageDto;
import mn.jwt.data.mappers.MessageMapper;
import mn.jwt.data.repositories.MessageRepository;
import mn.jwt.data.repositories.UserRepository;

@Singleton
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messagesRepository, MessageMapper messageMapper, UserRepository userRepository) {
        this.messageRepository = messagesRepository;
        this.messageMapper = messageMapper;
        this.userRepository = userRepository;
    }

    public List<MessageDto> findAll() {
        List<MessageDto> messageDtos = new ArrayList<>();
        messageRepository.findAll().forEach(message -> messageDtos.add(messageMapper.toDto(message)));
        return messageDtos;
    }

    public List<MessageDto> findAllByUsername(String username) {

        List<MessageDto> messageDtos = new ArrayList<>();

        userRepository.findByUsername(username).ifPresent(user ->
                messageRepository.findAllByUser(user).forEach(message ->
                        messageDtos.add(messageMapper.toDto(message)))
        );

        return messageDtos;
    }

    public Optional<MessageDto> create(String content, String username) {

         return userRepository.findByUsername(username).map(user ->
                                messageRepository.save(Message.builder().content(content).user(user).build()))
                                .map(messageMapper::toDto);
    }
}