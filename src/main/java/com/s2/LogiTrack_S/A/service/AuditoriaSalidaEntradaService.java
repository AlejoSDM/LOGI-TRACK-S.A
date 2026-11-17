package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import java.util.List;

public interface AuditoriaSalidaEntradaService {

    AuditoriaSalidaEntradaDtoResponse guardar(AuditoriaSalidaEntradaDtoRequest dto);
    AuditoriaSalidaEntradaDtoResponse actualizar(AuditoriaSalidaEntradaDtoRequest dto, Long id);
    void eliminar(Long id);
    List<AuditoriaSalidaEntradaDtoResponse> listar();
    AuditoriaSalidaEntradaDtoResponse buscarId(Long id);
}
