package com.encuesta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
