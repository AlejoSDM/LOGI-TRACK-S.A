package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaTransferenciaService {

    AuditoriaTransferenciaDtoResponse guardar(AuditoriaTransferenciaDtoRequest dto);
    List<AuditoriaTransferenciaDtoResponse> listar();
    AuditoriaTransferenciaDtoResponse buscarId(Long id);
    List<AuditoriaTransferenciaDtoResponse> buscarPorFecha(LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaTransferenciaDtoResponse> buscarPorUsuario(Long usuarioId);
    List<AuditoriaTransferenciaDtoResponse> buscarPorBodegas(Long bodegaId);
    List<AuditoriaTransferenciaDtoResponse> buscarPorUsuarioYFecha(
            Long usuarioId, LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaTransferenciaDtoResponse> buscarPorProducto(Long productoId);
    List<AuditoriaTransferenciaDtoResponse> buscarPorBodegaOrigen(Long bodegaId);
    List<AuditoriaTransferenciaDtoResponse> buscarPorBodegaDestino(Long bodegaId);
}
