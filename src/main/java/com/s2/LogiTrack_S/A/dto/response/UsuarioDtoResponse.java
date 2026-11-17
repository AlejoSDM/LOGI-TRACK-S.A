package com.s2.LogiTrack_S.A.dto.response;


public record UsuarioDtoResponse(
        Long id,
        Long personaId,
        String username,
        Boolean estado
) {
}
