package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.MarcaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import java.util.List;

public interface MarcaService {

    MarcaDtoResponse guardar(MarcaDtoRequest dto);
    MarcaDtoResponse actualizar(MarcaDtoRequest dto, Long id);
    void eliminar(Long id);
    List<MarcaDtoResponse> listar();
    MarcaDtoResponse buscarId(Long id);

}
