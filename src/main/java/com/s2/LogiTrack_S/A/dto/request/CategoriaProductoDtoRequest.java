package com.s2.LogiTrack_S.A.dto.request;

import com.s2.LogiTrack_S.A.model.CategoriaProducto.TipoCategoria;
import jakarta.validation.constraints.*;

public record CategoriaProductoDtoRequest(
        @NotNull(message = "El tipo de categoría no puede ser nulo")
        TipoCategoria tipoCategoria
) {
}