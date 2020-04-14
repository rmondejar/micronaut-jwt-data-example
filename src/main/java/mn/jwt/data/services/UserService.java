package mn.jwt.data.services;

import java.util.Optional;
import javax.inject.Singleton;

import mn.jwt.data.domain.User;
import mn.jwt.data.dtos.UserDto;
import mn.jwt.data.mappers.UserMapper;
import mn.jwt.data.repositories.UserRepository;

@Singleton
public class UserService {

    private final UserRepository usersRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(UserDto userDto) {
        User user = usersRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    public Optional<UserDto> findUser(String username) {
        return usersRepository.findByUsername(username).map(userMapper::toDto);
    }
}