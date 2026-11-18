package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.ProductosDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import java.util.List;

public interface ProductosService {
    ProductosDtoResponse guardar(ProductosDtoRequest dto);
    ProductosDtoResponse actualizar(ProductosDtoRequest dto, Long id);
    void eliminar(Long id);
    List<ProductosDtoResponse> listar();
    ProductosDtoResponse buscarId(Long id);
    List<ProductosDtoResponse> buscarPorNombre(String nombre);
    List<ProductosDtoResponse> buscarPorCategoria(Long categoriaId);
    List<ProductosDtoResponse> buscarPorMarca(Long marcaId);
}

