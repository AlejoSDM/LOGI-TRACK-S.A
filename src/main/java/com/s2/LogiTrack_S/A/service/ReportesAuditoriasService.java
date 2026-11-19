
package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportesAuditoriasService {

    void registrar(String modulo, String tipoAccion, Long registroId, String descripcion);
    List<ReportesAuditoriasDtoResponse> listar();
    ReportesAuditoriasDtoResponse buscarId(Long id);
    List<ReportesAuditoriasDtoResponse> buscarPorModulo(String modulo);
    List<ReportesAuditoriasDtoResponse> buscarPorTipoAccion(String tipoAccion);
    List<ReportesAuditoriasDtoResponse> buscarPorFecha(LocalDateTime desde, LocalDateTime hasta);
    List<ReportesAuditoriasDtoResponse> buscarPorUsuario(Long usuarioId);
}
