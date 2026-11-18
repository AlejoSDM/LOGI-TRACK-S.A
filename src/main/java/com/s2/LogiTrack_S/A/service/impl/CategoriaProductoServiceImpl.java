package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.CategoriaProductoDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.CategoriaProductoMapper;
import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import com.s2.LogiTrack_S.A.repository.CategoriaProductoRepository;
import com.s2.LogiTrack_S.A.service.CategoriaProductoService;
import com.s2.LogiTrack_S.A.validation.CategoriaProductoValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoMapper categoriaProductoMapper;
    private final CategoriaProductoRepository categoriaProductoRepository;
    private final CategoriaProductoValidation categoriaProductoValidation;

    @Override
    public CategoriaProductoDtoResponse guardar(CategoriaProductoDtoRequest dto) {
        CategoriaProducto categoriaProducto = categoriaProductoMapper.dtoToEntity(dto);
        categoriaProductoValidation.validarTipoCategoriaGuardar(dto.tipoCategoria());
        CategoriaProducto categoriaProductoGuardada = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.entityToDto(categoriaProductoGuardada);
    }

    @Override
    public CategoriaProductoDtoResponse actualizar(CategoriaProductoDtoRequest dto, Long id) {
        categoriaProductoValidation.validarId(id);
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Categoría no encontrada "));
        categoriaProductoValidation.validarTipoCategoriaActualizar(dto.tipoCategoria(), id);
        categoriaProductoMapper.updateEntityFromDto(categoriaProducto, dto);
        CategoriaProducto categoriaProductoActualizada = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.entityToDto(categoriaProductoActualizada);
    }

    @Override
    public void eliminar(Long id) {
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Categoría no encontrada "));
        categoriaProductoValidation.validarId(id);
        categoriaProductoRepository.delete(categoriaProducto);
    }

    @Override
    public List<CategoriaProductoDtoResponse> listar() {
        return categoriaProductoRepository.findAll().stream().map(categoriaProductoMapper::entityToDto).toList();
    }

    @Override
    public CategoriaProductoDtoResponse buscarId(Long id) {
        CategoriaProducto categoriaProducto = categoriaProductoRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("Categoría no encontrada "));
        return categoriaProductoMapper.entityToDto(categoriaProducto);
    }
}