package com.grupo3.forgotservice.email.service.iimpl;

import com.grupo3.forgotservice.email.service.ITemplateHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class TemplateHelperImpl implements ITemplateHelper {

    @Override
    public String getTemplate(String templateName) {
        try{
            ClassPathResource resource = new ClassPathResource(templateName);
            return new String(resource.getInputStream().readAllBytes());
        }catch (Exception e){
            throw new RuntimeException("Error cargando el template: " + templateName, e);
        }
    }
}
