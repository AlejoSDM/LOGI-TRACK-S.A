package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportesAuditoriasRepository extends JpaRepository<ReportesAuditorias, Long> {

    List<ReportesAuditorias> findByFechaBetween(
            LocalDateTime desde, LocalDateTime hasta);

    List<ReportesAuditorias> findByTransferenciaId_Id(Long transferenciaId);

    List<ReportesAuditorias> findBySalidaEntradaId_Id(Long salidaEntradaId);
}