package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.BodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.mapper.BodegaMapper;
import com.s2.LogiTrack_S.A.mapper.CiudadMapper;
import com.s2.LogiTrack_S.A.mapper.UsuarioMapper;
import com.s2.LogiTrack_S.A.mapper.PersonaMapper;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.CiudadRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import com.s2.LogiTrack_S.A.service.BodegaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor

public class BodegaServiceImpl implements BodegaService {
 private final BodegaMapper bodegaMapper; private final CiudadMapper ciudadMapper; private final UsuarioMapper usuarioMapper; private final PersonaMapper personaMapper;
 private final BodegaRepository bodegaRepository; private final CiudadRepository ciudadRepository; private final UsuarioRepository usuarioRepository;
 public BodegaDtoResponse guardar(BodegaDtoRequest dto) {
  var ciudad=ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new RuntimeException("No existe la ciudad"));
  var usuario=usuarioRepository.findById(dto.empleadoId()).orElseThrow(() -> new RuntimeException("No existe el empleado"));
  Bodega bodega=bodegaMapper.dtoToEntity(dto,ciudad,usuario); Bodega guardada=bodegaRepository.save(bodega);
  return bodegaMapper.entityToDto(guardada,ciudadMapper.entityToDto(ciudad),usuarioMapper.entityToDto(usuario,personaMapper.entityToDto(usuario.getPersonaId())));
 }
 public BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id) {
  Bodega b=bodegaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la bodega"));
  var ciudad=ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new RuntimeException("No existe la ciudad"));
  var usuario=usuarioRepository.findById(dto.empleadoId()).orElseThrow(() -> new RuntimeException("No existe el empleado"));
  bodegaMapper.updateEntityFromDto(b,dto,ciudad,usuario); Bodega guardada=bodegaRepository.save(b);
  return bodegaMapper.entityToDto(guardada,ciudadMapper.entityToDto(ciudad),usuarioMapper.entityToDto(usuario,personaMapper.entityToDto(usuario.getPersonaId())));
 }
 public void eliminar(Long id) { bodegaRepository.delete(bodegaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la bodega"))); }
 public List<BodegaDtoResponse> listar() { return bodegaRepository.findAll().stream().map(b -> bodegaMapper.entityToDto(b,ciudadMapper.entityToDto(b.getCiudadId()),usuarioMapper.entityToDto(b.getUsuarioId(),personaMapper.entityToDto(b.getUsuarioId().getPersonaId())))).toList(); }
 public BodegaDtoResponse buscarId(Long id) { Bodega b=bodegaRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la bodega")); return bodegaMapper.entityToDto(b,ciudadMapper.entityToDto(b.getCiudadId()),usuarioMapper.entityToDto(b.getUsuarioId(),personaMapper.entityToDto(b.getUsuarioId().getPersonaId()))); }
}
