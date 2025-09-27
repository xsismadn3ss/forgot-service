package com.grupo3.forgotservice.code.service.impl;

import com.grupo3.forgotservice.code.service.IRandomCodeService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RandomCodeServiceImpl implements IRandomCodeService {
    private static final Random random = new Random();

    @Override
    public String generateCode() {
        int number = random.nextInt(1_000_000);
        return String.format("%06d", number);
    }
}
