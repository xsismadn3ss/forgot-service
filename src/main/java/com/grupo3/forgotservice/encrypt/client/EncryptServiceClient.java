package com.grupo3.forgotservice.encrypt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shareddtos.usersmodule.auth.EncryptDto;
import shareddtos.usersmodule.auth.MessageDto;

@FeignClient(name = "encrypt-service", url = "${app.clients.encrypt-service-url}")
public interface EncryptServiceClient {
    @PostMapping
    MessageDto encrypt(@RequestBody EncryptDto encryptDto);
}
