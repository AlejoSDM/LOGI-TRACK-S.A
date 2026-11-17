package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.CategoriaProductoDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import java.util.List;

public interface CategoriaProductoService {

    CategoriaProductoDtoResponse guardar(CategoriaProductoDtoRequest dto);
    CategoriaProductoDtoResponse actualizar(CategoriaProductoDtoRequest dto, Long id);
    void eliminar(Long id);
    List<CategoriaProductoDtoResponse> listar();
    CategoriaProductoDtoResponse buscarId(Long id);
}
