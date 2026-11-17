package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;

public record MarcaDtoRequest(
        @NotBlank(message = "El nombre de la marca no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre de la marca debe tener entre 2 y 100 caracteres")
        String nombreMarca
) {
}