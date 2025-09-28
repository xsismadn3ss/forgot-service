package com.grupo3.forgotservice.user.service;

import shareddtos.usersmodule.auth.SimpleUserDto;

public interface IUserService {
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
