package mn.jwt.data.services;

import java.util.Optional;
import javax.inject.Singleton;

import lombok.extern.slf4j.Slf4j;
import mn.jwt.data.domain.User;
import mn.jwt.data.dtos.UserDto;
import mn.jwt.data.mappers.UserMapper;
import mn.jwt.data.repositories.UserRepository;

@Slf4j
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

    public Optional<UserDto> findByRefreshToken(String refreshToken) {
        return usersRepository.findByToken(refreshToken).map(userMapper::toDto);
    }

    public void saveRefreshToken(String username, String refreshToken) {
        usersRepository.findByUsername(username).ifPresent(
                user -> {
                    user.setToken(refreshToken);
                    usersRepository.update(user);
                }
        );
    }
}