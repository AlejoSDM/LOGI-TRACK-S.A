package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PersonaDtoRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String nombre
) {
}
