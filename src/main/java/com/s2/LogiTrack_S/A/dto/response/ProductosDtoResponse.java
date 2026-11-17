package com.s2.LogiTrack_S.A.dto.response;

import java.math.BigDecimal;

public record ProductosDtoResponse(
        Long id,
        String nombre,
        Long marcaId,
        Long categoriaId,
        BigDecimal precioUnitario
) {
}