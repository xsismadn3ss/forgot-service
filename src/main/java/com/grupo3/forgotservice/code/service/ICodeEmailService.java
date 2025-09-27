package com.grupo3.forgotservice.code.service;

public interface ICodeEmailService {
    void sendCodeEmail(String email, String code);
}
