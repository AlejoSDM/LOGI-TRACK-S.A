package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.CiudadDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import java.util.List;

public interface CiudadService {
    CiudadDtoResponse guardar(CiudadDtoRequest dto);
    CiudadDtoResponse actualizar(CiudadDtoRequest dto, Long id);
    void eliminar(Long id);
    List<CiudadDtoResponse> listar();
    CiudadDtoResponse buscarId(Long id);
}
