package com.grupo3.forgotservice.code.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    @NotBlank
    @NotNull
    @Length(min = 6)
    private String code;

    @NotBlank
    @NotNull
    @Length(min = 8)
    private String password;

    @NotBlank
    @NotNull
    @Email
    private String email;
}
