package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.BodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.BodegaMapper;
import com.s2.LogiTrack_S.A.mapper.CiudadMapper;
import com.s2.LogiTrack_S.A.mapper.UsuarioMapper;
import com.s2.LogiTrack_S.A.mapper.PersonaMapper;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Ciudad;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.CiudadRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import com.s2.LogiTrack_S.A.service.BodegaService;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import com.s2.LogiTrack_S.A.validation.BodegaValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class BodegaServiceImpl implements BodegaService {
    private final BodegaMapper bodegaMapper;
    private final BodegaValidation bodegaValidation;
    private final CiudadMapper ciudadMapper;
    private final UsuarioMapper usuarioMapper;
    private final PersonaMapper personaMapper;
    private final BodegaRepository bodegaRepository;
    private final CiudadRepository ciudadRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReportesAuditoriasService reportesAuditoriasService;

    @Override
    @Transactional
    public BodegaDtoResponse guardar(BodegaDtoRequest dto) {
           Ciudad c = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new DatabaseRuleException("No existe la ciudad"));
           Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el empleado"));
           bodegaValidation.validarNombreGuardar(dto.nombre(), dto.ciudadId());
           bodegaValidation.validarCapacidad(dto.capacidad(), null);
           bodegaValidation.validarEmpleado(u);
           Bodega bodega = bodegaMapper.dtoToEntity(dto, c, u);
           CiudadDtoResponse ciudadDto = ciudadMapper.entityToDto(c);
           UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
           Bodega bodegaGuardada = bodegaRepository.save(bodega);
            reportesAuditoriasService.registrar(
                    "BODEGA",
                    "CREAR",
                    bodegaGuardada.getId(),
                    "Se creó la bodega: " + bodegaGuardada.getNombre()
            );
            return bodegaMapper.entityToDto(bodegaGuardada, ciudadDto, usuarioDto);
    }

     @Override
     @Transactional
     public BodegaDtoResponse actualizar(BodegaDtoRequest dto, Long id) {
          Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
          bodegaValidation.validarNombreActualizar(dto.nombre(), dto.ciudadId(), id);
          bodegaValidation.validarCapacidad(dto.capacidad(), id);
          Ciudad c = ciudadRepository.findById(dto.ciudadId()).orElseThrow(() -> new DatabaseRuleException("No existe la ciudad"));
          Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el empleado"));
          bodegaValidation.validarEmpleado(u);
          CiudadDtoResponse ciudadDto = ciudadMapper.entityToDto(c);
          UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
          bodegaMapper.updateEntityFromDto(bodega, dto, c, u);
          Bodega bodegaActualizada = bodegaRepository.save(bodega);
          reportesAuditoriasService.registrar(
                 "BODEGA",
                 "MODIFICAR",
                 bodegaActualizada.getId(),
                 "Se modificó la bodega: " + bodegaActualizada.getNombre()
          );
         return bodegaMapper.entityToDto(bodegaActualizada, ciudadDto, usuarioDto);
     }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Bodega bodega = bodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        bodegaValidation.validarEliminar(id);
        Long bodegaId = bodega.getId();
        String nombreBodega = bodega.getNombre();
        bodegaRepository.delete(bodega);
        reportesAuditoriasService.registrar(
                "BODEGA",
                "ELIMINAR",
                bodegaId,
                "Se eliminó la bodega: " + nombreBodega
        );
    }

    @Override
    public List<BodegaDtoResponse> listar() {
        return bodegaRepository.findAll().stream().map
                (b -> bodegaMapper.entityToDto(b,
                        ciudadMapper.entityToDto(b.getCiudadId()),
                        usuarioMapper.entityToDto(b.getUsuarioId(),
                                personaMapper.entityToDto(b.getUsuarioId().getPersonaId())))).toList();
    }

    @Override
    public BodegaDtoResponse buscarId(Long id) {
        Bodega b = bodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        return bodegaMapper.entityToDto(b, ciudadMapper.entityToDto(b.getCiudadId()), usuarioMapper.entityToDto(b.getUsuarioId(), personaMapper.entityToDto(b.getUsuarioId().getPersonaId())));
    }

    @Override
    public List<BodegaDtoResponse> buscarNombre(String nombre) {
        return bodegaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(b -> bodegaMapper.entityToDto(
                        b,
                        ciudadMapper.entityToDto(b.getCiudadId()),
                        usuarioMapper.entityToDto(
                                b.getUsuarioId(),
                                personaMapper.entityToDto(b.getUsuarioId().getPersonaId())
                        )
                )).toList();
    }

    @Override
    public List<BodegaDtoResponse> buscarCiudad(Long ciudadId) {
        return bodegaRepository.findByCiudadId_IdCiudad(ciudadId)
                .stream()
                .map(b -> bodegaMapper.entityToDto(
                        b,
                        ciudadMapper.entityToDto(b.getCiudadId()),
                        usuarioMapper.entityToDto(
                                b.getUsuarioId(),
                                personaMapper.entityToDto(b.getUsuarioId().getPersonaId())
                        )
                )).toList();
    }

    @Override
    public List<BodegaDtoResponse> buscarEmpleado(Long usuarioId) {
        return bodegaRepository.findByUsuarioId_Id(usuarioId)
                .stream()
                .map(b -> bodegaMapper.entityToDto(
                        b,
                        ciudadMapper.entityToDto(b.getCiudadId()),
                        usuarioMapper.entityToDto(
                                b.getUsuarioId(),
                                personaMapper.entityToDto(b.getUsuarioId().getPersonaId())
                        )
                )).toList();
    }
}
