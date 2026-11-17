package com.s2.LogiTrack_S.A.dto.response;

import java.time.LocalDateTime;

public record AuditoriaTransferenciaDtoResponse(
        Long id,
        LocalDateTime fecha,
        Long usuarioId,
        Long bodegaOrigenId,
        Long bodegaDestinoId,
        Long productoId,
        Integer cantidad
) {
}
