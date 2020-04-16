package mn.jwt.data.mappers;

import mn.jwt.data.domain.Message;
import mn.jwt.data.dtos.MessageDto;

import javax.inject.Singleton;

@Singleton
public class MessageMapper {

    final private UserMapper userMapper;

    public MessageMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Message toEntity(MessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .content(messageDto.getContent())
                .creationDate(messageDto.getCreationDate())
                .user(userMapper.toEntity(messageDto.getUser()))
                .build();
    }

    public MessageDto toDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .creationDate(message.getCreationDate())
                .user(userMapper.toDto(message.getUser()))
                .build();
    }
}
