package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.InventarioBodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.InventarioBodegaDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.InventarioBodegaMapper;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.repository.InventarioBodegaRepository;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import com.s2.LogiTrack_S.A.model.InventarioBodega;
import com.s2.LogiTrack_S.A.service.InventarioBodegaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioBodegaServiceImpl implements InventarioBodegaService {

   private final InventarioBodegaRepository inventarioBodegaRepository;
   private final InventarioBodegaMapper inventarioBodegaMapper;
   private final BodegaRepository bodegaRepository;
   private final ProductosRepository productosRepository;

    @Override
    public InventarioBodegaDtoResponse guardar(InventarioBodegaDtoRequest dto) {
        Bodega b = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        InventarioBodega inventarioBodega = inventarioBodegaMapper.dtoToEntity(dto, b, p);
        InventarioBodega inventarioGuardado = inventarioBodegaRepository.save(inventarioBodega);
        return inventarioBodegaMapper.entityToDto(inventarioGuardado);
    }

    @Override
    public InventarioBodegaDtoResponse actualizar(InventarioBodegaDtoRequest dto, Long id) {
        InventarioBodega i = inventarioBodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el inventario de bodega"));
        Bodega b = bodegaRepository.findById(dto.bodegaId()).orElseThrow(() -> new DatabaseRuleException("No existe la bodega"));
        Productos p = productosRepository.findById(dto.productoId()).orElseThrow(() -> new DatabaseRuleException("No existe el producto"));
        inventarioBodegaMapper.updateEntityFromDto(i, dto, b, p);
        InventarioBodega inventarioActualizado = inventarioBodegaRepository.save(i);
        return inventarioBodegaMapper.entityToDto(inventarioActualizado);
    }

    @Override
    public void eliminar(Long id) {
        InventarioBodega i = inventarioBodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el inventario de bodega"));
        inventarioBodegaRepository.delete(i);
    }

    @Override
    public List<InventarioBodegaDtoResponse> listar() {
        return inventarioBodegaRepository.findAll().stream()
                .map(inventarioBodegaMapper::entityToDto)
                .toList();
    }


    @Override
    public InventarioBodegaDtoResponse buscarId(Long id) {
        InventarioBodega i = inventarioBodegaRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el inventario de bodega"));
        return inventarioBodegaMapper.entityToDto(i);
    }
}