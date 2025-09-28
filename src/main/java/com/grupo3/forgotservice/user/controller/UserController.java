package com.grupo3.forgotservice.user.controller;

import com.grupo3.forgotservice.user.dto.UpdatePasswordDto;
import com.grupo3.forgotservice.code.service.ICacheCodeService;
import com.grupo3.forgotservice.encrypt.client.EncryptServiceClient;
import com.grupo3.forgotservice.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import shareddtos.usersmodule.auth.EncryptDto;
import shareddtos.usersmodule.auth.MessageDto;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired private IUserService userService;
    @Autowired private ICacheCodeService cacheCodeService;
    @Autowired private EncryptServiceClient encryptServiceClient;

    @PostMapping("/update")
    @Operation(
            summary = "Enviar código de recuperación y actualizar contraseña",
            description = "Devuelve un mensaje, notificando que la contraseña ha sido actualizada"
    )
    public ResponseEntity<MessageDto> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        // validar código
        String saved_code = cacheCodeService.getCode(updatePasswordDto.getEmail());
        if(saved_code == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El código no existe o ha expirado");
        } else if(!saved_code.equals(updatePasswordDto.getCode())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código incorrecto");
        }

        // actualizar contraseña
        MessageDto data = encryptServiceClient.encrypt( new EncryptDto(updatePasswordDto.getPassword()));
        userService.updatePassword(updatePasswordDto.getEmail(), data.getMessage());
        cacheCodeService.deleteCode(updatePasswordDto.getEmail());
        MessageDto message = new MessageDto("Contraseña actualizada con éxito");
        return ResponseEntity.ok(message);
    }
}
