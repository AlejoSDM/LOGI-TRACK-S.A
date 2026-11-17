package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.request.ProductosDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.ProductosMapper;
import com.s2.LogiTrack_S.A.mapper.MarcaMapper;
import com.s2.LogiTrack_S.A.mapper.CategoriaProductoMapper;
import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import com.s2.LogiTrack_S.A.model.Marca;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import com.s2.LogiTrack_S.A.repository.MarcaRepository;
import com.s2.LogiTrack_S.A.repository.CategoriaProductoRepository;
import com.s2.LogiTrack_S.A.service.ProductosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductosServiceImpl implements ProductosService {

    private final ProductosMapper productosMapper;
    private final MarcaMapper marcaMapper;
    private final CategoriaProductoMapper categoriaMapper;
    private final ProductosRepository productosRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaProductoRepository categoriaRepository;

    @Override
    public ProductosDtoResponse guardar(ProductosDtoRequest dto) {
        Marca m = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new DatabaseRuleException("No existe la marca "));
        CategoriaProducto c = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new DatabaseRuleException("No existe la categoría "));
        Productos productos = productosMapper.dtoToEntity(dto, m, c);
        MarcaDtoResponse marcaDtoResponse = marcaMapper.entityToDto(m);
        CategoriaProductoDtoResponse categoriaDtoResponse = categoriaMapper.entityToDto(c);
        return productosMapper.entityToDto(productosRepository.save(productos), marcaDtoResponse, categoriaDtoResponse);

    }

    @Override
    public ProductosDtoResponse actualizar(ProductosDtoRequest dto, Long id) {
       Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto: "));
       Marca m = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new DatabaseRuleException("No existe la marca "));
       CategoriaProducto c = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new DatabaseRuleException("No existe la categoría "));
       MarcaDtoResponse marcaDtoResponse = marcaMapper.entityToDto(m);
       CategoriaProductoDtoResponse categoriaDtoResponse = categoriaMapper.entityToDto(c);
       productosMapper.updateEntityFromDto(p, dto, m, c);
       return productosMapper.entityToDto(productosRepository.save(p), marcaDtoResponse, categoriaDtoResponse);
    }

    @Override
    public void eliminar(Long id) {
        Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto: "));
        productosRepository.delete(p);
    }

    @Override
    public List<ProductosDtoResponse> listar() {
        return productosRepository.findAll().stream().map
                (p -> productosMapper.entityToDto(p,
                        marcaMapper.entityToDto(p.getMarcaId()),
                        categoriaMapper.entityToDto(p.getCategoriaId()))).toList();
    }

    @Override
    public ProductosDtoResponse buscarId(Long id) {
        Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto: " + id));
        return productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
    }
}
