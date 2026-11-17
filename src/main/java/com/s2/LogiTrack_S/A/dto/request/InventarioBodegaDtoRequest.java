package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;

public record InventarioBodegaDtoRequest(
        @NotNull(message = "La bodega no puede ser nula")
        @Positive(message = "El ID de la bodega debe ser un número positivo")
        Long bodegaId,

        @NotNull(message = "El producto no puede ser nulo")
        @Positive(message = "El ID del producto debe ser un número positivo")
        Long productoId,

        @NotNull(message = "El stock no puede ser nulo")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock
) {
}