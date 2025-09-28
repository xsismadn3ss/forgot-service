package com.grupo3.forgotservice.code.controller;

import com.grupo3.forgotservice.code.dto.EmailDto;
import com.grupo3.forgotservice.code.service.ICacheCodeService;
import com.grupo3.forgotservice.code.service.ICodeEmailService;
import com.grupo3.forgotservice.code.service.IRandomCodeService;
import com.grupo3.forgotservice.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shareddtos.usersmodule.auth.MessageDto;
import shareddtos.usersmodule.auth.SimpleUserDto;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired private IUserService userService;
    @Autowired private ICacheCodeService cacheCodeService;
    @Autowired private IRandomCodeService randomCodeService;
    @Autowired private ICodeEmailService codeEmailService;

    @PostMapping("/send")
    @Operation(
            summary = "Solicitar código para recuperar cuenta",
            description = "Devuelve un mensaje, notificando que el código fue generado y ha sido enviado al email"
    )
    public ResponseEntity<MessageDto> requestCode(@Valid @RequestBody EmailDto emailDto) {
        SimpleUserDto user = userService.findByEmail(emailDto.getEmail());
        String code = randomCodeService.generateCode();
        cacheCodeService.saveCode(code, user.getEmail());
        codeEmailService.sendCodeEmail(user.getEmail(), code);

        MessageDto message = new MessageDto("Código generado con éxito, revise su correo");
        return ResponseEntity.ok(message);
    }
}
