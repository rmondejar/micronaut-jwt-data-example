package mn.jwt.data.mappers;

import mn.jwt.data.domain.User;
import mn.jwt.data.dtos.UserDto;

import javax.inject.Singleton;

@Singleton
public class UserMapper {

    public User toEntity(UserDto userDto) {
        return User.builder().username(userDto.getUsername()).password(userDto.getPassword()).role(userDto.getRole()).build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder().username(user.getUsername()).password(user.getPassword()).role(user.getRole()).build();
    }
}
