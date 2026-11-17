package com.s2.LogiTrack_S.A.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ReportesAuditoriasDtoRequest(

        @NotNull(message = "La fecha no puede ser nula")
        LocalDateTime fecha,

        @Positive(message = "El ID de la transferencia debe ser un número positivo")
        Long transferenciaId,

        @Positive(message = "El ID de salida/entrada debe ser un número positivo")
        Long salidaEntradaId
) {
}