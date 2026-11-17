package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.ReportesAuditoriasDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.*;
import com.s2.LogiTrack_S.A.mapper.*;
import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import com.s2.LogiTrack_S.A.repository.*;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportesAuditoriasServiceImpl implements ReportesAuditoriasService {
    private final ReportesAuditoriasMapper mapper;
    private final AuditoriaTransferenciaServiceImpl transferenciaService;
    private final AuditoriaSalidaEntradaServiceImpl salidaService;
    private final ReportesAuditoriasRepository repository;
    private final AuditoriaTransferenciaRepository transferenciaRepository;
    private final AuditoriaSalidaEntradaRepository salidaRepository;

    /*NOS AYUDA A NO TENER QUE REPETIR LO MISMO SIEMPRE*/
    private ReportesAuditoriasDtoResponse respuesta(ReportesAuditorias r) {
        /*Si r.getTransferenciaId() es null, asignamos null.
        Si tiene una transferencia asociada, buscamos su información mediante buscarId().*/
        AuditoriaTransferenciaDtoResponse transferencia = r.getTransferenciaId() == null ? null : transferenciaService.buscarId(r.getTransferenciaId().getId());
        AuditoriaSalidaEntradaDtoResponse salida = r.getSalidaEntradaId() == null ? null : salidaService.buscarId(r.getSalidaEntradaId().getId());
        /*ACA ENTREGAMOS LA ENTIDAD Y LOS DOS DTO SI UNO EXISTE Y EL OTRO NO*/
        return mapper.entityToDto(r, transferencia, salida);
    }

    /*Si el ID es null, devuelve null
    Si tiene ID, busca la auditoría en su Repository.
    Si no existe, lanza una excepción.
    respuesta() evita repetir la conversión a DTO.*/


    private com.s2.LogiTrack_S.A.model.AuditoriaTransferencia transferencia(Long id) {
        return id == null ? null : transferenciaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la transferencia "));
    }


    private com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada salidaEntrada(Long id) {
        return id == null ? null : salidaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la auditoría de entrada/salida "));
    }

    @Override
    public ReportesAuditoriasDtoResponse guardar(ReportesAuditoriasDtoRequest dto) {
        ReportesAuditorias r = mapper.dtoToEntity(dto, salidaEntrada(dto.salidaEntradaId()), transferencia(dto.transferenciaId()));
        return respuesta(repository.save(r));
    }

    @Override
    public ReportesAuditoriasDtoResponse actualizar(ReportesAuditoriasDtoRequest dto, Long id) {
        ReportesAuditorias r = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el reporte "));
        mapper.updateEntityFromDto(r, dto, salidaEntrada(dto.salidaEntradaId()), transferencia(dto.transferenciaId()));
        return respuesta(repository.save(r));
    }

    @Override
    public void eliminar(Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el reporte ")));
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> listar() {
        return repository.findAll().stream().map(this::respuesta).toList();
    }

    @Override
    public ReportesAuditoriasDtoResponse buscarId(Long id) {
        return respuesta(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe el reporte ")));
    }
}
