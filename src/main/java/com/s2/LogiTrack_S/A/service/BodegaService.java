package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.BodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import java.util.List;

public interface BodegaService { BodegaDtoResponse guardar(BodegaDtoRequest dto);
    BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id);
    void eliminar(Long id);
    List<BodegaDtoResponse> listar();
    BodegaDtoResponse buscarId(Long id); }
