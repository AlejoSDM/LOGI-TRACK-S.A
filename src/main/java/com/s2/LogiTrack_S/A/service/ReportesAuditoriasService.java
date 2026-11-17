package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.ReportesAuditoriasDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import java.util.List;

public interface ReportesAuditoriasService {
    ReportesAuditoriasDtoResponse guardar(ReportesAuditoriasDtoRequest dto);
    ReportesAuditoriasDtoResponse actualizar(ReportesAuditoriasDtoRequest dto, Long id);
    void eliminar(Long id);
    List<ReportesAuditoriasDtoResponse> listar();
    ReportesAuditoriasDtoResponse buscarId(Long id);
}
