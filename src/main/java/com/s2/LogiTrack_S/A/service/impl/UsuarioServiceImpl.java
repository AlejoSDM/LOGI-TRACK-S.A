package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.UsuarioDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.UsuarioMapper;
import com.s2.LogiTrack_S.A.mapper.PersonaMapper;
import com.s2.LogiTrack_S.A.model.Persona;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.PersonaRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import com.s2.LogiTrack_S.A.service.UsuarioService;
import com.s2.LogiTrack_S.A.validation.UsuarioValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;
    private final UsuarioValidation usuarioValidation;

    @Override
    public UsuarioDtoResponse guardar(UsuarioDtoRequest dto) {
        usuarioValidation.validarGuardar(dto.username(), dto.personaId());
        Persona p = personaRepository.findById(dto.personaId()).orElseThrow(()-> new DatabaseRuleException("Codigo de persona no existe"));
        Usuario usuario = usuarioMapper.DtoToEntity(dto, p);
        PersonaDtoResponse personaDtoResponse = personaMapper.entityToDto(p);
        return usuarioMapper.entityToDto(usuarioRepository.save(usuario), personaDtoResponse);
    }

    @Override
    public UsuarioDtoResponse actualizar(UsuarioDtoRequest dto, Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el usuario: " ));
        usuarioValidation.validarActualizar(dto.username(), dto.personaId(), id);
        Persona p = personaRepository.findById(dto.personaId()).orElseThrow(() -> new DatabaseRuleException("Codigo de persona no existe"));
        PersonaDtoResponse personaDtoResponse = personaMapper.entityToDto(p);
        usuarioMapper.updateEntityFromDto(usuario, dto, p);
        return usuarioMapper.entityToDto(usuarioRepository.save(usuario), personaDtoResponse);
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el usuario "));
        usuarioValidation.validarEliminar(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<UsuarioDtoResponse> listar() {
        return usuarioRepository.findAll().stream().map
                        (usuario -> usuarioMapper.entityToDto(usuario,
                        personaMapper.entityToDto(usuario.getPersonaId()))).toList();
    }

    @Override
    public UsuarioDtoResponse buscarId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el usuario "));
        return usuarioMapper.entityToDto(usuario, personaMapper.entityToDto(usuario.getPersonaId()));
    }

}
