package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.CiudadDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.CiudadMapper;
import com.s2.LogiTrack_S.A.model.Ciudad;
import com.s2.LogiTrack_S.A.repository.CiudadRepository;
import com.s2.LogiTrack_S.A.service.CiudadService;
import com.s2.LogiTrack_S.A.validation.CiudadValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CiudadServiceImpl implements CiudadService {
    private final CiudadMapper ciudadMapper;
    private final CiudadRepository ciudadRepository;
    private final CiudadValidation ciudadValidation;

    @Override
    public CiudadDtoResponse guardar(CiudadDtoRequest dto) {
        ciudadValidation.validarNombreGuardar(dto.nombreCiudad());
        Ciudad ciudad = ciudadMapper.dtoToEntity(dto);
        Ciudad ciudadGuardada = ciudadRepository.save(ciudad);
        return ciudadMapper.entityToDto(ciudadGuardada);
    }

    @Override
    public CiudadDtoResponse actualizar(CiudadDtoRequest dto, Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Ciudad no encontrada"));
        ciudadValidation.validarNombreActualizar(dto.nombreCiudad(), id);
        ciudadMapper.updateEntityFromDto(ciudad, dto);
        Ciudad ciudadActualizada = ciudadRepository.save(ciudad);
        return ciudadMapper.entityToDto(ciudadActualizada);
    }

    @Override
    public void eliminar(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Ciudad no encontrada"));
        ciudadValidation.validarEliminar(id);
        ciudadRepository.delete(ciudad);
    }

    @Override
    public List<CiudadDtoResponse> listar() {
        return ciudadRepository.findAll().stream().map(ciudadMapper::entityToDto).toList();
    }

    @Override
    public CiudadDtoResponse buscarId(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Ciudad no encontrada"));
        return ciudadMapper.entityToDto(ciudad);
    }
}
