package com.s2.LogiTrack_S.A.service.impl;
import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import com.s2.LogiTrack_S.A.mapper.*;
import com.s2.LogiTrack_S.A.model.AuditoriaTransferencia;
import com.s2.LogiTrack_S.A.repository.*;
import com.s2.LogiTrack_S.A.service.AuditoriaTransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class AuditoriaTransferenciaServiceImpl implements AuditoriaTransferenciaService {
 private final AuditoriaTransferenciaMapper mapper; private final UsuarioMapper usuarioMapper; private final PersonaMapper personaMapper; private final BodegaMapper bodegaMapper; private final CiudadMapper ciudadMapper; private final ProductosMapper productosMapper; private final MarcaMapper marcaMapper; private final CategoriaProductoMapper categoriaMapper;
 private final AuditoriaTransferenciaRepository repository; private final UsuarioRepository usuarioRepository; private final BodegaRepository bodegaRepository; private final ProductosRepository productosRepository;
 private com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse bodegaDto(com.s2.LogiTrack_S.A.model.Bodega b) { return bodegaMapper.entityToDto(b,ciudadMapper.entityToDto(b.getCiudadId()),usuarioMapper.entityToDto(b.getUsuarioId(),personaMapper.entityToDto(b.getUsuarioId().getPersonaId()))); }
 private AuditoriaTransferenciaDtoResponse respuesta(AuditoriaTransferencia a) { return mapper.entityToDto(a,usuarioMapper.entityToDto(a.getUsuarioId(),personaMapper.entityToDto(a.getUsuarioId().getPersonaId())),bodegaDto(a.getBodegaOrigenId()),bodegaDto(a.getBodegaDestinoId()),productosMapper.entityToDto(a.getProductoId(),marcaMapper.entityToDto(a.getProductoId().getMarcaId()),categoriaMapper.entityToDto(a.getProductoId().getCategoriaId()))); }
 public AuditoriaTransferenciaDtoResponse guardar(AuditoriaTransferenciaDtoRequest dto) { var u=usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RuntimeException("No existe el usuario")); var origen=bodegaRepository.findById(dto.bodegaOrigenId()).orElseThrow(() -> new RuntimeException("No existe la bodega de origen")); var destino=bodegaRepository.findById(dto.bodegaDestinoId()).orElseThrow(() -> new RuntimeException("No existe la bodega de destino")); var p=productosRepository.findById(dto.productoId()).orElseThrow(() -> new RuntimeException("No existe el producto")); return respuesta(repository.save(mapper.dtoToEntity(dto,u,origen,destino,p))); }
 public AuditoriaTransferenciaDtoResponse actualizar(AuditoriaTransferenciaDtoRequest dto, Long id) { AuditoriaTransferencia a=repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la transferencia")); var u=usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RuntimeException("No existe el usuario")); var origen=bodegaRepository.findById(dto.bodegaOrigenId()).orElseThrow(() -> new RuntimeException("No existe la bodega de origen")); var destino=bodegaRepository.findById(dto.bodegaDestinoId()).orElseThrow(() -> new RuntimeException("No existe la bodega de destino")); var p=productosRepository.findById(dto.productoId()).orElseThrow(() -> new RuntimeException("No existe el producto")); mapper.updateEntityFromDto(a,dto,u,origen,destino,p); return respuesta(repository.save(a)); }
 public void eliminar(Long id) { repository.delete(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la transferencia"))); }
 public List<AuditoriaTransferenciaDtoResponse> listar() { return repository.findAll().stream().map(this::respuesta).toList(); }
 public AuditoriaTransferenciaDtoResponse buscarId(Long id) { return respuesta(repository.findById(id).orElseThrow(() -> new RuntimeException("No existe la transferencia"))); }
}
