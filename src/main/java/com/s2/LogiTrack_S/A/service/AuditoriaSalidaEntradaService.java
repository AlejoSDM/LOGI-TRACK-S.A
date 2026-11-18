package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaSalidaEntradaService {

    AuditoriaSalidaEntradaDtoResponse guardar(AuditoriaSalidaEntradaDtoRequest dto);
    List<AuditoriaSalidaEntradaDtoResponse> listar();
    AuditoriaSalidaEntradaDtoResponse buscarId(Long id);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorFecha(LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorUsuario(Long usuarioId);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorTipoAccion(
            AuditoriaSalidaEntrada.TipoAccion tipoAccion);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorUsuarioYFecha(
            Long usuarioId, LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorBodega(Long bodegaId);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorProducto(Long productoId);
    List<AuditoriaSalidaEntradaDtoResponse> buscarPorBodegaYFecha(
            Long bodegaId, LocalDateTime desde, LocalDateTime hasta);
}
