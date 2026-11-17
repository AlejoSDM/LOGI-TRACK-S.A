package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.*;
import com.s2.LogiTrack_S.A.model.AuditoriaTransferencia;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.*;
import com.s2.LogiTrack_S.A.service.AuditoriaTransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuditoriaTransferenciaServiceImpl implements AuditoriaTransferenciaService {
    private final AuditoriaTransferenciaMapper auditoriaTransferenciaMapper;
    private final UsuarioMapper usuarioMapper;
    private final PersonaMapper personaMapper;
    private final BodegaMapper bodegaMapper;
    private final CiudadMapper ciudadMapper;
    private final ProductosMapper productosMapper;
    private final MarcaMapper marcaMapper;
    private final CategoriaProductoMapper categoriaMapper;
    private final AuditoriaTransferenciaRepository auditoriaTransferenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final ProductosRepository productosRepository;

    @Override
    public AuditoriaTransferenciaDtoResponse guardar(AuditoriaTransferenciaDtoRequest dto) {
        Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el usuario"));
        Bodega origen = bodegaRepository.findById(dto.bodegaOrigenId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega de origen"));
        Bodega destino = bodegaRepository.findById(dto.bodegaDestinoId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega de destino"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        AuditoriaTransferencia a = auditoriaTransferenciaMapper.dtoToEntity(dto, u, origen, destino, p);
        UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
        BodegaDtoResponse bodegaOrigenDto = bodegaMapper.entityToDto(origen, ciudadMapper.entityToDto(origen.getCiudadId()), usuarioMapper.entityToDto(origen.getUsuarioId(), personaMapper.entityToDto(origen.getUsuarioId().getPersonaId())));
        BodegaDtoResponse bodegaDestinoDto = bodegaMapper.entityToDto(destino, ciudadMapper.entityToDto(destino.getCiudadId()), usuarioMapper.entityToDto(destino.getUsuarioId(), personaMapper.entityToDto(destino.getUsuarioId().getPersonaId())));
        ProductosDtoResponse productoDto = productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
        return auditoriaTransferenciaMapper.entityToDto(auditoriaTransferenciaRepository.save(a), usuarioDto, bodegaOrigenDto, bodegaDestinoDto, productoDto);
    }


    @Override
    public AuditoriaTransferenciaDtoResponse actualizar(AuditoriaTransferenciaDtoRequest dto, Long id) {
        AuditoriaTransferencia a = auditoriaTransferenciaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la transferencia"));
        Usuario u = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new DatabaseRuleException("No existe el usuario"));
        Bodega origen = bodegaRepository.findById(dto.bodegaOrigenId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega de origen"));
        Bodega destino = bodegaRepository.findById(dto.bodegaDestinoId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega de destino"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        UsuarioDtoResponse usuarioDto = usuarioMapper.entityToDto(u, personaMapper.entityToDto(u.getPersonaId()));
        BodegaDtoResponse bodegaOrigenDto = bodegaMapper.entityToDto(origen, ciudadMapper.entityToDto(origen.getCiudadId()), usuarioMapper.entityToDto(origen.getUsuarioId(), personaMapper.entityToDto(origen.getUsuarioId().getPersonaId())));
        BodegaDtoResponse bodegaDestinoDto = bodegaMapper.entityToDto(destino, ciudadMapper.entityToDto(destino.getCiudadId()), usuarioMapper.entityToDto(destino.getUsuarioId(), personaMapper.entityToDto(destino.getUsuarioId().getPersonaId())));
        ProductosDtoResponse productoDto = productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
        auditoriaTransferenciaMapper.updateEntityFromDto(a, dto, u, origen, destino, p);
        return auditoriaTransferenciaMapper.entityToDto(auditoriaTransferenciaRepository.save(a), usuarioDto, bodegaOrigenDto, bodegaDestinoDto, productoDto
        );
    }

    @Override
    public void eliminar(Long id) {
       AuditoriaTransferencia a = auditoriaTransferenciaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la transferencia"));
         auditoriaTransferenciaRepository.delete(a);
    }

    @Override
    public List<AuditoriaTransferenciaDtoResponse> listar() {
     return auditoriaTransferenciaRepository.findAll().stream().map
             (a -> auditoriaTransferenciaMapper.entityToDto(a,
                     usuarioMapper.entityToDto(a.getUsuarioId(), personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),
                     bodegaMapper.entityToDto(a.getBodegaOrigenId(), ciudadMapper.entityToDto(a.getBodegaOrigenId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaOrigenId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaOrigenId().getUsuarioId().getPersonaId()))),
                     bodegaMapper.entityToDto(a.getBodegaDestinoId(), ciudadMapper.entityToDto(a.getBodegaDestinoId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaDestinoId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaDestinoId().getUsuarioId().getPersonaId()))),
                     productosMapper.entityToDto(a.getProductoId(), marcaMapper.entityToDto(a.getProductoId().getMarcaId()), categoriaMapper.entityToDto(a.getProductoId().getCategoriaId())))).toList();
    }

    @Override
    public AuditoriaTransferenciaDtoResponse buscarId(Long id) {
        AuditoriaTransferencia a = auditoriaTransferenciaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe la transferencia"));
        return auditoriaTransferenciaMapper.entityToDto(a,
                usuarioMapper.entityToDto(a.getUsuarioId(), personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),
                bodegaMapper.entityToDto(a.getBodegaOrigenId(), ciudadMapper.entityToDto(a.getBodegaOrigenId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaOrigenId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaOrigenId().getUsuarioId().getPersonaId()))),
                bodegaMapper.entityToDto(a.getBodegaDestinoId(), ciudadMapper.entityToDto(a.getBodegaDestinoId().getCiudadId()), usuarioMapper.entityToDto(a.getBodegaDestinoId().getUsuarioId(), personaMapper.entityToDto(a.getBodegaDestinoId().getUsuarioId().getPersonaId()))),
                productosMapper.entityToDto(a.getProductoId(), marcaMapper.entityToDto(a.getProductoId().getMarcaId()), categoriaMapper.entityToDto(a.getProductoId().getCategoriaId())));
    }
}
