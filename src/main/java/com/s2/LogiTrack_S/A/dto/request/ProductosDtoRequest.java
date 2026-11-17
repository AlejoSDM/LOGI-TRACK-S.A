package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductosDtoRequest(
        @NotBlank(message = "El nombre del producto no puede estar vacío")
        @Size(min = 2, max = 150, message = "El nombre del producto debe tener entre 2 y 150 caracteres")
        String nombre,

        @NotNull(message = "La marca no puede ser nula")
        @Positive(message = "El ID de la marca debe ser un número positivo")
        Long marcaId,

        @NotNull(message = "La categoría no puede ser nula")
        @Positive(message = "El ID de la categoría debe ser un número positivo")
        Long categoriaId,

        @NotNull(message = "El precio unitario no puede ser nulo")
        @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor a 0")
        BigDecimal precioUnitario
) {
}