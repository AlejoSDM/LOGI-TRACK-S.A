package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.*;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.*;
import com.s2.LogiTrack_S.A.service.AuditoriaSalidaEntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaSalidaEntradaServiceImpl implements AuditoriaSalidaEntradaService {

    private final AuditoriaSalidaEntradaMapper auditoriaSalidaEntradaMapper;
    private final UsuarioMapper usuarioMapper;
    private final PersonaMapper personaMapper;
    private final BodegaMapper bodegaMapper;
    private final CiudadMapper ciudadMapper;
    private final ProductosMapper productosMapper;
    private final MarcaMapper marcaMapper;
    private final CategoriaProductoMapper categoriaMapper;

    private final AuditoriaSalidaEntradaRepository auditoriaSalidaEntradaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final ProductosRepository productosRepository;

    @Override
    public AuditoriaSalidaEntradaDtoResponse guardar(AuditoriaSalidaEntradaDtoRequest dto) {
        Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el usuario"));
        Bodega b = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        AuditoriaSalidaEntrada a = auditoriaSalidaEntradaMapper.dtoToEntity(dto, u, b, p);
        UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
        BodegaDtoResponse bodegaDto = bodegaMapper.entityToDto(b, ciudadMapper.entityToDto(b.getCiudadId()), usuarioMapper.entityToDto(b.getUsuarioId(), personaMapper.entityToDto(b.getUsuarioId().getPersonaId())));
        ProductosDtoResponse productoDto = productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
        return auditoriaSalidaEntradaMapper.entityToDto(auditoriaSalidaEntradaRepository.save(a), usuarioDto, bodegaDto, productoDto);
    }

    @Override
    public AuditoriaSalidaEntradaDtoResponse actualizar(AuditoriaSalidaEntradaDtoRequest dto, Long id) {
        AuditoriaSalidaEntrada a = auditoriaSalidaEntradaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la auditoría"));
        Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el usuario"));
        Bodega b = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
        BodegaDtoResponse bodegaDto = bodegaMapper.entityToDto(b, ciudadMapper.entityToDto(b.getCiudadId()), usuarioMapper.entityToDto(b.getUsuarioId(), personaMapper.entityToDto(b.getUsuarioId().getPersonaId())));
        ProductosDtoResponse productoDto = productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
        auditoriaSalidaEntradaMapper.updateEntityFromDto(a, dto, u, b, p);
        return auditoriaSalidaEntradaMapper.entityToDto(auditoriaSalidaEntradaRepository.save(a), usuarioDto, bodegaDto, productoDto);
    }

    @Override
    public void eliminar(Long id) {
        AuditoriaSalidaEntrada a = auditoriaSalidaEntradaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la auditoría"));
        auditoriaSalidaEntradaRepository.delete(a);
    }

    @Override
    public List<AuditoriaSalidaEntradaDtoResponse> listar() {
        return auditoriaSalidaEntradaRepository.findAll().stream().map(a ->
                auditoriaSalidaEntradaMapper.entityToDto(a,
                        usuarioMapper.entityToDto(a.getUsuarioId(), personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),
                        bodegaMapper.entityToDto(a.getBodegaId(), ciudadMapper.entityToDto(a.getBodegaId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaId().getUsuarioId().getPersonaId()))),
                        productosMapper.entityToDto(a.getProductoId(), marcaMapper.entityToDto(a.getProductoId().getMarcaId()), categoriaMapper.entityToDto(a.getProductoId().getCategoriaId())))).toList();
    }

    @Override
    public AuditoriaSalidaEntradaDtoResponse buscarId(Long id) {
        AuditoriaSalidaEntrada a = auditoriaSalidaEntradaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la auditoría"));
        return auditoriaSalidaEntradaMapper.entityToDto(a,
                usuarioMapper.entityToDto(a.getUsuarioId(), personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),
                bodegaMapper.entityToDto(a.getBodegaId(), ciudadMapper.entityToDto(a.getBodegaId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaId().getUsuarioId().getPersonaId()))),
                productosMapper.entityToDto(a.getProductoId(), marcaMapper.entityToDto(a.getProductoId().getMarcaId()), categoriaMapper.entityToDto(a.getProductoId().getCategoriaId())));
    }
}
