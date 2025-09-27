package com.grupo3.forgotservice.user.service.impl;

import com.grupo3.forgotservice.user.entity.User;
import com.grupo3.forgotservice.user.service.IUserMapper;
import org.springframework.stereotype.Service;
import shareddtos.usersmodule.auth.UserDto;

@Service
public class UserMapperImpl implements IUserMapper {

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getName());
        userDto.setLastName(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
