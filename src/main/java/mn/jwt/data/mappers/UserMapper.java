package mn.jwt.data.mappers;

import mn.jwt.data.domain.User;
import mn.jwt.data.dtos.UserDto;

import javax.inject.Singleton;

@Singleton
public class UserMapper {

    public User toEntity(UserDto userDto) {
        User user = User.builder().username(userDto.getUsername()).password(userDto.getPassword()).role(userDto.getRole()).build();
        if (user.getRole()==null) {
            user.setRole(User.DEFAULT_ROLE);
        }
        return user;
    }

    public UserDto toDto(User user) {
        return UserDto.builder().username(user.getUsername()).password(user.getPassword()).role(user.getRole()).build();
    }
}
