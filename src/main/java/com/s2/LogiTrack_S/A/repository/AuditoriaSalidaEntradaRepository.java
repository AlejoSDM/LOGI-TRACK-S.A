package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaSalidaEntradaRepository extends JpaRepository<AuditoriaSalidaEntrada, Long> {
    List<AuditoriaSalidaEntrada> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
    List<AuditoriaSalidaEntrada> findByUsuarioId_Id(Long usuarioId);
    List<AuditoriaSalidaEntrada> findByTipoAccion(AuditoriaSalidaEntrada.TipoAccion tipoAccion);
    List<AuditoriaSalidaEntrada> findByUsuarioId_IdAndFechaBetween(Long usuarioId, LocalDateTime desde, LocalDateTime hasta);
}
