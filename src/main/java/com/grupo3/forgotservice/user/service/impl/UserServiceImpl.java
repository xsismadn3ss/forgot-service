package com.grupo3.forgotservice.user.service.impl;

import com.grupo3.forgotservice.user.entity.User;
import com.grupo3.forgotservice.user.repository.UserRepository;
import com.grupo3.forgotservice.user.service.IUserMapper;
import com.grupo3.forgotservice.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shareddtos.usersmodule.auth.SimpleUserDto;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserMapper userMapper;

    @Override
    public Optional<SimpleUserDto> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(
                value -> Optional.of(userMapper.toDto(value).toSimpleUserDto()))
                .orElse(null);
    }

    @Override
    public Optional<SimpleUserDto> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(
                value -> Optional.of(userMapper.toDto(value).toSimpleUserDto()))
                .orElse(null);
    }

    @Override
    public SimpleUserDto updatePassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        );
        user.setPassword(password);
        userRepository.save(user);
        return userMapper.toDto(user).toSimpleUserDto();
    }
}
