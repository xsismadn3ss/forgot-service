package com.grupo3.forgotservice.email.service.iimpl;

import com.grupo3.forgotservice.email.service.ITemplateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateHelperImpl implements ITemplateHelper {
    private static final String TEMPLATE_PATH = "templates/";

    @Override
    public String getTemplate(String templateName) {
        String fullPath = TEMPLATE_PATH + templateName;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fullPath).getInputStream(), StandardCharsets.UTF_8))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (Exception e) {
            log.error("Error cargando el template '{}': {}", templateName, e.getMessage());
            throw new RuntimeException("Error cargando el template: " + templateName, e);
        }
    }
}
