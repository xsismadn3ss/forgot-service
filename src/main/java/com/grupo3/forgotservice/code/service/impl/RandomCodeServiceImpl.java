package com.grupo3.forgotservice.code.service.impl;

import com.grupo3.forgotservice.code.service.IRandomCodeService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomCodeServiceImpl implements IRandomCodeService {
    private static final Random random = new Random();

    @Override
    public String generateCode() {
        int number = random.nextInt(1_000_0000);
        return String.format("%06d", number);
    }
}
