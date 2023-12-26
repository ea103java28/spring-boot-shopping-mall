package com.tony.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    @Schema(description = "the email address of user", example = "test@gmail.com", required = true)
    @NotBlank
    @Email
    private String email;

    @Schema(description = "the password of user", example = "123456", minLength = 6, required = true)
    @NotBlank
    @Min(6)
    private String password;
}
