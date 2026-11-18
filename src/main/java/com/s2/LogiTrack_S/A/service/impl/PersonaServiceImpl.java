package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.PersonaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.PersonaMapper;
import com.s2.LogiTrack_S.A.model.Persona;
import com.s2.LogiTrack_S.A.repository.PersonaRepository;
import com.s2.LogiTrack_S.A.service.PersonaService;
import com.s2.LogiTrack_S.A.validation.PersonaValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PersonaServiceImpl implements PersonaService {

    private final PersonaMapper personaMapper;
    private final PersonaRepository personaRepository;
    private final PersonaValidation personaValidation;

    @Override
    public PersonaDtoResponse guardar(PersonaDtoRequest dto) {
       Persona p = personaMapper.dtoToEntity(dto);
       Persona PersonaDtoResponse = personaRepository.save(p);
       return personaMapper.entityToDto(PersonaDtoResponse);
    }

    @Override
    public PersonaDtoResponse actualizar(PersonaDtoRequest dto, Long id) {
        Persona p = personaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Persona no encontrada: "));
        personaMapper.updateEntityFromDto(p, dto);
        Persona personaActualizada = personaRepository.save(p);
        return personaMapper.entityToDto(personaActualizada);
    }

    @Override
    public void eliminar(Long id) {
        Persona p = personaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Persona no encontrada: "));
        personaValidation.validarEliminar(id);
        personaRepository.delete(p);
    }

    @Override
    public List<PersonaDtoResponse> listar() {
        return personaRepository.findAll().stream().map(personaMapper::entityToDto).toList(); }

    @Override
    public PersonaDtoResponse buscarId(Long id) {
        Persona p = personaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Persona no encontrada: "));
        return personaMapper.entityToDto(p);
    }

}
