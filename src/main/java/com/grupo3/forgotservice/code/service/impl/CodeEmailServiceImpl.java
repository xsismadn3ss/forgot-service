package com.grupo3.forgotservice.code.service.impl;

import com.grupo3.forgotservice.code.service.ICodeEmailService;
import com.grupo3.forgotservice.email.service.IEmailHelper;
import com.grupo3.forgotservice.email.service.ITemplateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CodeEmailServiceImpl implements ICodeEmailService {
    @Autowired
    private IEmailHelper emailHelper;
    @Autowired
    private ITemplateHelper templateHelper;


    @Override
    public void sendCodeEmail(String email, String code) {
        String htmlContent = templateHelper.getTemplate("forgot-password.html");
        emailHelper.sendEmail(
                email,
                "Código de recuperación",
                htmlContent.replace("{{code}}", code)
        );
    }
}
