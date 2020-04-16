package mn.jwt.data.services;

import java.util.List;
import java.util.ArrayList;

import javax.inject.Singleton;

import mn.jwt.data.dtos.MessageDto;
import mn.jwt.data.mappers.MessageMapper;
import mn.jwt.data.repositories.MessageRepository;
import mn.jwt.data.repositories.UserRepository;

@Singleton
public class MessageService {

    private final MessageRepository messagesRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messagesRepository, MessageMapper messageMapper, UserRepository userRepository) {
        this.messagesRepository = messagesRepository;
        this.messageMapper = messageMapper;
        this.userRepository = userRepository;
    }

    public List<MessageDto> findAll() {
        List<MessageDto> messageDtos = new ArrayList<>();
        messagesRepository.findAll().forEach(message -> messageDtos.add(messageMapper.toDto(message)));
        return messageDtos;
    }

    public List<MessageDto> findAllByUsername(String username) {

        List<MessageDto> messageDtos = new ArrayList<>();

        userRepository.findByUsername(username).ifPresent(user ->
                messagesRepository.findAllByUser(user).forEach(message ->
                        messageDtos.add(messageMapper.toDto(message)))
        );

        return messageDtos;
    }
}