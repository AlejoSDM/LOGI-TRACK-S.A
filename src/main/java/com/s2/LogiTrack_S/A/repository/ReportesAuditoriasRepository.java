
package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportesAuditoriasRepository
        extends JpaRepository<ReportesAuditorias, Long> {

    List<ReportesAuditorias> findAllByOrderByFechaDesc();

    List<ReportesAuditorias> findByModuloOrderByFechaDesc(
            String modulo
    );

    List<ReportesAuditorias> findByTipoAccionOrderByFechaDesc(
            String tipoAccion
    );

    List<ReportesAuditorias> findByFechaBetweenOrderByFechaDesc(
            LocalDateTime desde,
            LocalDateTime hasta
    );

    List<ReportesAuditorias> findByUsuarioId_IdOrderByFechaDesc(
            Long usuarioId
    );
}
