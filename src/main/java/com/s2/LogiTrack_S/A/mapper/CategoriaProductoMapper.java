package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.CategoriaProductoDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProductoMapper {
    public CategoriaProductoDtoResponse entityToDto(CategoriaProducto categoriaProducto) {
        if (categoriaProducto == null) return null;
        return new CategoriaProductoDtoResponse(
                categoriaProducto.getId(),
                categoriaProducto.getTipoCategoria()
        );
    }

    public CategoriaProducto dtoToEntity(CategoriaProductoDtoRequest dto) {
        if (dto == null) return null;
        CategoriaProducto categoriaProducto = new CategoriaProducto();
        categoriaProducto.setTipoCategoria(dto.tipoCategoria());
        return categoriaProducto;
    }

    public void updateEntityFromDto(CategoriaProducto categoriaProducto, CategoriaProductoDtoRequest dto) {
        if (categoriaProducto == null || dto == null) return;
        categoriaProducto.setTipoCategoria(dto.tipoCategoria());
    }
}
