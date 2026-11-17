package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;

public record BodegaDtoRequest(
        @NotBlank(message = "El nombre de la bodega no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre de la bodega debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotNull(message = "La ciudad no puede ser nula")
        @Positive(message = "El ID de la ciudad debe ser un número positivo")
        Long ciudadId,

        @NotNull(message = "La capacidad no puede ser nula")
        @Positive(message = "La capacidad debe ser un número positivo")
        Integer capacidad,

        @NotNull(message = "El empleado no puede ser nulo")
        @Positive(message = "El ID del empleado debe ser un número positivo")
        Long empleadoId
) {
}