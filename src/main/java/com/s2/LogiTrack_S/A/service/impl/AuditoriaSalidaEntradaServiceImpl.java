package com.s2.LogiTrack_S.A.service.impl;
import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.mapper.AuditoriaSalidaEntradaMapper;
import com.s2.LogiTrack_S.A.mapper.UsuarioMapper;
import com.s2.LogiTrack_S.A.mapper.PersonaMapper;
import com.s2.LogiTrack_S.A.mapper.BodegaMapper;
import com.s2.LogiTrack_S.A.mapper.CiudadMapper;
import com.s2.LogiTrack_S.A.mapper.ProductosMapper;
import com.s2.LogiTrack_S.A.mapper.MarcaMapper;
import com.s2.LogiTrack_S.A.mapper.CategoriaProductoMapper;
import com.s2.LogiTrack_S.A.repository.AuditoriaSalidaEntradaRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.service.AuditoriaSalidaEntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class AuditoriaSalidaEntradaServiceImpl implements AuditoriaSalidaEntradaService {
 private final AuditoriaSalidaEntradaMapper mapper; private final UsuarioMapper usuarioMapper; private final PersonaMapper personaMapper; private final BodegaMapper bodegaMapper; private final CiudadMapper ciudadMapper; private final ProductosMapper productosMapper; private final MarcaMapper marcaMapper; private final CategoriaProductoMapper categoriaMapper;
 private final AuditoriaSalidaEntradaRepository repository; private final UsuarioRepository usuarioRepository; private final BodegaRepository bodegaRepository; private final ProductosRepository productosRepository;
 private AuditoriaSalidaEntradaDtoResponse respuesta(AuditoriaSalidaEntrada a) { return mapper.entityToDto(a,usuarioMapper.entityToDto(a.getUsuarioId(),personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),bodegaMapper.entityToDto(a.getBodegaId(),ciudadMapper.entityToDto(a.getBodegaId().getCiudadId()),usuarioMapper.entityToDto(a.getBodegaId().getUsuarioId(),personaMapper.entityToDto(a.getBodegaId().getUsuarioId().getPersonaId()))),productosMapper.entityToDto(a.getProductoId(),marcaMapper.entityToDto(a.getProductoId().getMarcaId()),categoriaMapper.entityToDto(a.getProductoId().getCategoriaId()))); }
 public AuditoriaSalidaEntradaDtoResponse guardar(AuditoriaSalidaEntradaDtoRequest dto) { var u=usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RuntimeException("No existe el usuario")); var b=bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new RuntimeException("No existe la bodega")); var p=productosRepository.findById(dto.productoId()).orElseThrow(() -> new RuntimeException("No existe el producto")); return respuesta(repository.save(mapper.dtoToEntity(dto,u,b,p))); }
 public AuditoriaSalidaEntradaDtoResponse actualizar(AuditoriaSalidaEntradaDtoRequest dto, Long id) { var a=repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la auditoría")); var u=usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RuntimeException("No existe el usuario")); var b=bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new RuntimeException("No existe la bodega")); var p=productosRepository.findById(dto.productoId()).orElseThrow(() -> new RuntimeException("No existe el producto")); mapper.updateEntityFromDto(a,dto,u,b,p); return respuesta(repository.save(a)); }
 public void eliminar(Long id) { repository.delete(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la auditoría"))); }
 public List<AuditoriaSalidaEntradaDtoResponse> listar() { return repository.findAll().stream().map(this::respuesta).toList(); }
 public AuditoriaSalidaEntradaDtoResponse buscarId(Long id) { return respuesta(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la auditoría"))); }
}
