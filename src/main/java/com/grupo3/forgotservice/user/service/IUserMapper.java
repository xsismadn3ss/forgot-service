package com.grupo3.forgotservice.user.service;
import com.grupo3.forgotservice.user.entity.User;
import shareddtos.usersmodule.auth.UserDto;

public interface IUserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
