package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.ReportesAuditoriasDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.model.AuditoriaTransferencia;
import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import org.springframework.stereotype.Component;

@Component
public class ReportesAuditoriasMapper {
    public ReportesAuditoriasDtoResponse entityToDto (ReportesAuditorias reportesAuditorias, AuditoriaTransferenciaDtoResponse auditoriaTransferenciaDtoResponse,
                                                      AuditoriaSalidaEntradaDtoResponse auditoriaSalidaEntradaDtoResponse) {
        if (reportesAuditorias==null) return null;
        return new ReportesAuditoriasDtoResponse(
                reportesAuditorias.getId(),
                reportesAuditorias.getFecha(),
                auditoriaTransferenciaDtoResponse,
                auditoriaSalidaEntradaDtoResponse);
    }


    public ReportesAuditorias dtoToEntity (ReportesAuditoriasDtoRequest dto, AuditoriaSalidaEntrada auditoriaSalidaEntrada,
                                           AuditoriaTransferencia auditoriaTransferencia){
        if(dto==null) return null;
        ReportesAuditorias ra=new ReportesAuditorias();
        ra.setFecha(dto.fecha());
        ra.setSalidaEntradaId(auditoriaSalidaEntrada);
        ra.setTransferenciaId(auditoriaTransferencia);
        return ra;
    }

    public void updateEntityFromDto(ReportesAuditorias reportesAuditorias, ReportesAuditoriasDtoRequest dto, AuditoriaSalidaEntrada auditoriaSalidaEntrada,
                                           AuditoriaTransferencia auditoriaTransferencia){
        if(reportesAuditorias==null || dto==null) return;
        reportesAuditorias.setFecha(dto.fecha());
        reportesAuditorias.setSalidaEntradaId(auditoriaSalidaEntrada);
        reportesAuditorias.setTransferenciaId(auditoriaTransferencia);
    }

 }
