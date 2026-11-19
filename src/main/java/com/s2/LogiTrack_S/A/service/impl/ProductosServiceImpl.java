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
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import com.s2.LogiTrack_S.A.validation.ProductosValidation;
import jakarta.transaction.Transactional;
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
    private final ProductosValidation productosValidation;
    private final ReportesAuditoriasService reportesAuditoriasService;

    @Override
    @Transactional
    public ProductosDtoResponse guardar(ProductosDtoRequest dto) {
        Marca m = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new DatabaseRuleException("No existe la marca "));
        CategoriaProducto c = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new DatabaseRuleException("No existe la categoría "));
        productosValidation.validarNombreGuardar(dto.nombre(), dto.marcaId());
        Productos productos = productosMapper.dtoToEntity(dto, m, c);
        MarcaDtoResponse marcaDtoResponse = marcaMapper.entityToDto(m);
        CategoriaProductoDtoResponse categoriaDtoResponse = categoriaMapper.entityToDto(c);
        Productos guardado = productosRepository.save(productos);
        reportesAuditoriasService.registrar(
                "PRODUCTO",
                "CREAR",
                guardado.getId(),
                "Se creó el producto: " + guardado.getNombre()
        );
        return productosMapper.entityToDto(guardado, marcaDtoResponse, categoriaDtoResponse);
    }

    @Override
    @Transactional
    public ProductosDtoResponse actualizar(ProductosDtoRequest dto, Long id) {
       Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto: "));
       Marca m = marcaRepository.findById(dto.marcaId()).orElseThrow(() -> new DatabaseRuleException("No existe la marca "));
       CategoriaProducto c = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new DatabaseRuleException("No existe la categoría "));
       productosValidation.validarNombreActualizar(dto.nombre(), dto.marcaId(), id);
       MarcaDtoResponse marcaDtoResponse = marcaMapper.entityToDto(m);
       CategoriaProductoDtoResponse categoriaDtoResponse = categoriaMapper.entityToDto(c);
       productosMapper.updateEntityFromDto(p, dto, m, c);
        Productos actualizado = productosRepository.save(p);
        reportesAuditoriasService.registrar(
                "PRODUCTO",
                "MODIFICAR",
                actualizado.getId(),
                "Se modificó el producto: " + actualizado.getNombre()
        );
        return productosMapper.entityToDto(actualizado, marcaDtoResponse, categoriaDtoResponse);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto: "));
        productosValidation.validarEliminar(id);
        Long productoId = p.getId();
        String nombreProducto = p.getNombre();
        productosRepository.delete(p);
        reportesAuditoriasService.registrar(
                "PRODUCTO",
                "ELIMINAR",
                productoId,
                "Se eliminó el producto: " + nombreProducto
        );
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
        Productos p = productosRepository.findById(id).orElseThrow(() -> new DatabaseRuleException("No existe el producto "));
        return productosMapper.entityToDto(p, marcaMapper.entityToDto(p.getMarcaId()), categoriaMapper.entityToDto(p.getCategoriaId()));
    }


    @Override
    public List<ProductosDtoResponse> buscarPorNombre(String nombre) {

        return productosRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(p -> productosMapper.entityToDto(
                        p,
                        marcaMapper.entityToDto(p.getMarcaId()),
                        categoriaMapper.entityToDto(p.getCategoriaId())
                )).toList();
    }



    @Override
    public List<ProductosDtoResponse> buscarPorCategoria(Long categoriaId) {

        return productosRepository.findByCategoriaId_Id(categoriaId)
                .stream()
                .map(p -> productosMapper.entityToDto(
                        p,
                        marcaMapper.entityToDto(p.getMarcaId()),
                        categoriaMapper.entityToDto(p.getCategoriaId())
                )).toList();
    }


    @Override
    public List<ProductosDtoResponse> buscarPorMarca(Long marcaId) {

        return productosRepository.findByMarcaId_Id(marcaId)
                .stream()
                .map(p -> productosMapper.entityToDto(
                        p,
                        marcaMapper.entityToDto(p.getMarcaId()),
                        categoriaMapper.entityToDto(p.getCategoriaId())
                )).toList();
    }
}
