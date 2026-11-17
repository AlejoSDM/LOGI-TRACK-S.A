package com.s2.LogiTrack_S.A.dto.response;

import java.time.LocalDateTime;

public record AuditoriaSalidaEntradaDtoResponse(
        Long id,
        LocalDateTime fecha,
        String tipoAccion,
        Long usuarioId,
        Long bodegaId,
        Long productoId,
        Integer cantidad
) {
}