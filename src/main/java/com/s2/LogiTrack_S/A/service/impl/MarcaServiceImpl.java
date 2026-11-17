package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.MarcaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.MarcaMapper;
import com.s2.LogiTrack_S.A.model.Marca;
import com.s2.LogiTrack_S.A.repository.MarcaRepository;
import com.s2.LogiTrack_S.A.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MarcaServiceImpl implements MarcaService {

    private final MarcaMapper marcaMapper;
    private final MarcaRepository marcaRepository;

    @Override
    public MarcaDtoResponse guardar(MarcaDtoRequest dto) {
        Marca m = marcaMapper.dtoToEntity(dto);
        Marca marcaDtoResponse = marcaRepository.save(m);
        return marcaMapper.entityToDto(marcaDtoResponse);
    }

    @Override
    public MarcaDtoResponse actualizar(MarcaDtoRequest dto, Long id) {
        Marca m = marcaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Marca no encontrada: "));
        marcaMapper.updateEntityFromDto(m, dto);
        Marca marcaActualizada = marcaRepository.save(m);
        return marcaMapper.entityToDto(marcaActualizada);
    }

    @Override
    public void eliminar(Long id) {
        Marca m = marcaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Marca no encontrada: "));
        marcaRepository.delete(m);
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
}
