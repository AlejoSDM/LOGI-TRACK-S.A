package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AuditoriaTransferenciaDtoRequest(
        @NotNull(message = "El usuario responsable no puede ser nulo")
        @Positive(message = "El ID del usuario responsable debe ser un número positivo")
        Long usuarioId,

        @NotNull(message = "La fecha no puede ser nula")
        LocalDateTime fecha,

        @NotNull(message = "La bodega de origen no puede ser nula")
        @Positive(message = "El ID de la bodega de origen debe ser un número positivo")
        Long bodegaOrigenId,

        @NotNull(message = "La bodega de destino no puede ser nula")
        @Positive(message = "El ID de la bodega de destino debe ser un número positivo")
        Long bodegaDestinoId,

        @NotNull(message = "El producto no puede ser nulo")
        @Positive(message = "El ID del producto debe ser un número positivo")
        Long productoId,

        @NotNull(message = "La cantidad no puede ser nula")
        @Positive(message = "La cantidad debe ser un número positivo")
        Integer cantidad
) {
}