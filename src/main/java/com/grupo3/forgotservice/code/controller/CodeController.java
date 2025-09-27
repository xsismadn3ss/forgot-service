package com.grupo3.forgotservice.code.controller;

import com.grupo3.forgotservice.code.dto.EmailDto;
import com.grupo3.forgotservice.code.dto.UpdatePasswordDto;
import com.grupo3.forgotservice.code.service.ICacheCodeService;
import com.grupo3.forgotservice.code.service.ICodeEmailService;
import com.grupo3.forgotservice.code.service.IRandomCodeService;
import com.grupo3.forgotservice.encrypt.client.EncryptServiceClient;
import com.grupo3.forgotservice.user.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import shareddtos.usersmodule.auth.SimpleUserDto;

@Slf4j
@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private IUserService userService;
    @Autowired private ICacheCodeService cacheCodeService;
    @Autowired private IRandomCodeService randomCodeService;
    @Autowired private ICodeEmailService codeEmailService;
    @Autowired private EncryptServiceClient encryptServiceClient;

    @PostMapping("/send")
    public ResponseEntity<MessageDto> requestCode(@Valid @RequestBody EmailDto emailDto) {
        SimpleUserDto user = userService.findByEmail(emailDto.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
        );
        String code = randomCodeService.generateCode();
        cacheCodeService.saveCode(code, user.getEmail());
        codeEmailService.sendCodeEmail(user.getEmail(), code);

        MessageDto message = new MessageDto("Código generado con éxito, revise su correo");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageDto> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto){
        // validar código
        try{
            String saved_code = cacheCodeService.getCode(updatePasswordDto.getEmail());
            if(!saved_code.equals(updatePasswordDto.getCode())){
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código incorrecto");
            }

            // actualizar contraseña
            MessageDto data = encryptServiceClient.encrypt( new EncryptDto(updatePasswordDto.getPassword()));
            userService.updatePassword(updatePasswordDto.getEmail(), data.getMessage());
            cacheCodeService.deleteCode(updatePasswordDto.getEmail());
            MessageDto message = new MessageDto("Contraseña actualizada con éxito");
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
