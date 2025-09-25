package com.grupo3.forgotservice.email.service;

public interface IEmailHelper {
    void sendEmail(String to, String subject, String body);
}
