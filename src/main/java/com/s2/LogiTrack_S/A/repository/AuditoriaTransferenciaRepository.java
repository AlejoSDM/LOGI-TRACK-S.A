package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.AuditoriaTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaTransferenciaRepository extends JpaRepository<AuditoriaTransferencia, Long> {

    List<AuditoriaTransferencia> findByFechaBetween(
            LocalDateTime desde, LocalDateTime hasta);

    List<AuditoriaTransferencia> findByUsuarioId_Id(Long usuarioId);

    List<AuditoriaTransferencia> findByBodegaOrigenId_IdOrBodegaDestinoId_Id(
            Long origenId, Long destinoId);

    List<AuditoriaTransferencia> findByUsuarioId_IdAndFechaBetween(
            Long usuarioId, LocalDateTime desde, LocalDateTime hasta);

    List<AuditoriaTransferencia> findByProductoId_Id(Long productoId);

    List<AuditoriaTransferencia> findByBodegaOrigenId_Id(Long bodegaId);

    List<AuditoriaTransferencia> findByBodegaDestinoId_Id(Long bodegaId);

    boolean existsByBodegaOrigenId_IdOrBodegaDestinoId_Id(
            Long origenId, Long destinoId);

    boolean existsByUsuarioId_Id(Long usuarioId);
}