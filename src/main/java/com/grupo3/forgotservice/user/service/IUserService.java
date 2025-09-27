package com.grupo3.forgotservice.user.service;

import shareddtos.usersmodule.auth.SimpleUserDto;

import java.util.Optional;

public interface IUserService {
    /**
     * Buscar usuario por username, devuelve nulo o datos
     * de un usuario
     * @param username nombre de usuario
     * @return datos de usuario o nulo
     */
    Optional<SimpleUserDto> findByUsername(String username);


    /**
     * Buscar usuario por email
     * @param email email
     * @return nulo o datos de usuario
     */
    SimpleUserDto findByEmail(String email);

    /**
     * Cambiar contraseña de un usuario
     * @param email email
     * @param password nueva contraseña
     * @return datos de usuario
     */
    SimpleUserDto updatePassword(String email, String password);
}
