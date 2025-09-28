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

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserMapper userMapper;

    @Override
    public SimpleUserDto findByEmail(String email) throws ResponseStatusException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        );
        return userMapper.toDto(user).toSimpleUserDto();
    }

    @Override
    public SimpleUserDto updatePassword(String email, String password) throws ResponseStatusException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        );
        if(password.equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña no puede ser igual a la anterior");
        }
        user.setPassword(password);
        userRepository.save(user);
        return userMapper.toDto(user).toSimpleUserDto();
    }
}
