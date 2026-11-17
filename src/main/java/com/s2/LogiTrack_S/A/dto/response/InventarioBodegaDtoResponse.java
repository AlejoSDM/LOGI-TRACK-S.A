package com.s2.LogiTrack_S.A.dto.response;

public record InventarioBodegaDtoResponse(
        Long id,
        Long bodegaId,
        Long productoId,
        Integer stock
) {
}