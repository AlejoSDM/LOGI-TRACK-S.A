package com.s2.LogiTrack_S.A.dto.request;

import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada.TipoAccion;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record AuditoriaSalidaEntradaDtoRequest(
        @NotNull(message = "El tipo de acción no puede ser nulo")
        TipoAccion tipoAccion,

        @NotNull(message = "La fecha no puede ser nula")
        LocalDateTime fecha,

        @NotNull(message = "El usuario responsable no puede ser nulo")
        @Positive(message = "El ID del usuario responsable debe ser un número positivo")
        Long usuarioId,

        @NotNull(message = "La bodega no puede ser nula")
        @Positive(message = "El ID de la bodega debe ser un número positivo")
        Long bodegaId,

        @NotNull(message = "El producto no puede ser nulo")
        @Positive(message = "El ID del producto debe ser un número positivo")
        Long productoId,

        @NotNull(message = "La cantidad no puede ser nula")
        @Positive(message = "La cantidad debe ser un número positivo")
        Integer cantidad
) {
}