
package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import org.springframework.stereotype.Component;

@Component
public class ReportesAuditoriasMapper {

    public ReportesAuditoriasDtoResponse entityToDto(
            ReportesAuditorias reporte
    ) {

        if (reporte == null) {
            return null;
        }

        return new ReportesAuditoriasDtoResponse(
                reporte.getId(),
                reporte.getFecha(),
                reporte.getUsuarioId().getId(),
                reporte.getUsuarioId().getUsername(),
                reporte.getModulo(),
                reporte.getTipoAccion(),
                reporte.getRegistroId(),
                reporte.getDescripcion()
        );
    }
}
