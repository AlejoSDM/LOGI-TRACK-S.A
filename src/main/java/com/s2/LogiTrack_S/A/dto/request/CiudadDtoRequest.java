package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CiudadDtoRequest(
        @NotBlank(message = "El nombre de la ciudad no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre de la ciudad no puede tener más de 100 caracteres")
        String nombreCiudad
) {
}
