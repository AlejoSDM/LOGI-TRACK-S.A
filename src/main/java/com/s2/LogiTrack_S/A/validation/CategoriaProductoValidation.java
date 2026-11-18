
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import com.s2.LogiTrack_S.A.repository.CategoriaProductoRepository;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaProductoValidation {

    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ProductosRepository productosRepository;

    public void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "El ID debe ser un número positivo"
            );
        }
    }

    public void validarTipoCategoriaGuardar(
            CategoriaProducto.TipoCategoria tipoCategoria) {

        if (tipoCategoria == null) {
            throw new BusinessRuleException(
                    "Debe proporcionar un tipo de categoría"
            );
        }

        if (categoriaProductoRepository.existsByTipoCategoria(tipoCategoria)) {
            throw new BusinessRuleException(
                    "Ya existe una categoría con ese tipo"
            );
        }
    }

    public void validarTipoCategoriaActualizar(
            CategoriaProducto.TipoCategoria tipoCategoria, Long id) {

        if (tipoCategoria == null) {
            throw new BusinessRuleException(
                    "Debe proporcionar un tipo de categoría"
            );
        }

        if (categoriaProductoRepository.existsByTipoCategoriaAndIdNot(
                tipoCategoria, id)) {
            throw new BusinessRuleException(
                    "Ya existe otra categoría con ese tipo"
            );
        }
    }

    public void validarEliminar(Long categoriaId) {
        if (productosRepository.existsByCategoriaId_Id(categoriaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una categoría que tiene productos asociados"
            );
        }
    }
}
