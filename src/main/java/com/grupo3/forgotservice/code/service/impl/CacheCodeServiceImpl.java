package com.grupo3.forgotservice.code.service.impl;

import com.grupo3.forgotservice.code.service.ICacheCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CacheCodeServiceImpl implements ICacheCodeService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveCode(String code, String email) {
        redisTemplate.opsForValue().set(email+"_forgot", code, Duration.ofMinutes(5));
    }

    @Override
    public String getCode(String email) {
        return (String) redisTemplate.opsForValue().get(email+"_forgot");
    }
}
