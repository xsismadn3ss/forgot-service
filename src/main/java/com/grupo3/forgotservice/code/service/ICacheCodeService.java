package com.grupo3.forgotservice.code.service;

public interface ICacheCodeService {
    void saveCode(String code, String email);
    String getCode(String email);
    void deleteCode(String email);
}
