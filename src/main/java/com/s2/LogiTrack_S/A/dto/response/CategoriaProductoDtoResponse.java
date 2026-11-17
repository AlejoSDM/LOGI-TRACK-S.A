package com.s2.LogiTrack_S.A.dto.response;

import com.s2.LogiTrack_S.A.model.CategoriaProducto;

public record CategoriaProductoDtoResponse(
        Long id,
        CategoriaProducto.TipoCategoria tipoCategoria
) {
}