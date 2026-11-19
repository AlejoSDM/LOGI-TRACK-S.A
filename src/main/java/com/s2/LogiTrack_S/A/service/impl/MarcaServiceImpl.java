package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.MarcaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.MarcaMapper;
import com.s2.LogiTrack_S.A.model.Marca;
import com.s2.LogiTrack_S.A.repository.MarcaRepository;
import com.s2.LogiTrack_S.A.service.MarcaService;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import com.s2.LogiTrack_S.A.validation.MarcaValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MarcaServiceImpl implements MarcaService {

    private final MarcaMapper marcaMapper;
    private final MarcaRepository marcaRepository;
    private final MarcaValidation marcaValidation;
    private final ReportesAuditoriasService reportesAuditoriasService;

    @Override
    @Transactional
    public MarcaDtoResponse guardar(MarcaDtoRequest dto) {
        marcaValidation.validarNombreGuardar(dto.nombreMarca());
        Marca m = marcaMapper.dtoToEntity(dto);
        Marca guardada = marcaRepository.save(m);
        reportesAuditoriasService.registrar(
                "MARCA",
                "CREAR",
                guardada.getId(),
                "Se creó la marca: " + guardada.getNombreMarca()
        );
        return marcaMapper.entityToDto(guardada);
    }

    @Override
    @Transactional
    public MarcaDtoResponse actualizar(MarcaDtoRequest dto, Long id) {
        Marca m = marcaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Marca no encontrada: "));
        marcaValidation.validarNombreActualizar(dto.nombreMarca(), id);
        marcaMapper.updateEntityFromDto(m, dto);
        Marca marcaActualizada = marcaRepository.save(m);
        reportesAuditoriasService.registrar(
                "MARCA",
                "MODIFICAR",
                marcaActualizada.getId(),
                "Se modificó la marca: " + marcaActualizada.getNombreMarca()
        );
        return marcaMapper.entityToDto(marcaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Marca m = marcaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Marca no encontrada: "));
        marcaValidation.validarEliminar(id);
        Long marcaId = m.getId();
        String nombreMarca = m.getNombreMarca();
        marcaRepository.delete(m);
        reportesAuditoriasService.registrar(
                "MARCA",
                "ELIMINAR",
                marcaId,
                "Se eliminó la marca: " + nombreMarca
        );
    }

    @Override
    public List<MarcaDtoResponse> listar() {
        return marcaRepository.findAll().stream()
                .map(marcaMapper::entityToDto)
                .toList();
    }

    @Override
    public MarcaDtoResponse buscarId(Long id) {
        Marca m = marcaRepository.findById(id)
                .orElseThrow(() -> new DatabaseRuleException("Marca no encontrada: "));
        return marcaMapper.entityToDto(m);
    }

    @Override
    public List<MarcaDtoResponse> buscarPorNombre(String nombreMarca) {
        return marcaRepository.findByNombreMarcaContainingIgnoreCase(nombreMarca)
                .stream()
                .map(marcaMapper::entityToDto)
                .toList();
    }
}
