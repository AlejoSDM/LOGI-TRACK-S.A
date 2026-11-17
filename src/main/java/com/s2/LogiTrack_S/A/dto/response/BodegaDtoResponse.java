package com.s2.LogiTrack_S.A.dto.response;

public record BodegaDtoResponse(
        Long id,
        String nombre,
        Long ciudadId,
        Integer capacidad,
        Long usuarioId
) {
}