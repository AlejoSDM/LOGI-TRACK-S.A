
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.InventarioBodega;
import com.s2.LogiTrack_S.A.repository.InventarioBodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventarioBodegaValidation {

    private final InventarioBodegaRepository inventarioBodegaRepository;

    public void validarStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new BusinessRuleException(
                    "El stock no puede ser nulo ni negativo"
            );
        }
    }

    public void validarGuardar(Long bodegaId, Long productoId) {
        if (inventarioBodegaRepository
                .existsByBodegaId_IdAndProductoId_Id(bodegaId, productoId)) {

            throw new BusinessRuleException(
                    "Este producto ya tiene un inventario registrado en esta bodega"
            );
        }
    }

    public void validarActualizar(
            Long bodegaId,
            Long productoId,
            Long id,
            InventarioBodega inventario
    ) {

        if (inventarioBodegaRepository
                .existsByBodegaId_IdAndProductoId_IdAndIdNot(
                        bodegaId, productoId, id)) {

            throw new BusinessRuleException(
                    "Este producto ya tiene un inventario registrado en esta bodega"
            );
        }

        boolean cambioBodega =
                !inventario.getBodegaId().getId().equals(bodegaId);

        boolean cambioProducto =
                !inventario.getProductoId().getId().equals(productoId);

        if (inventario.getStock() > 0 &&
                (cambioBodega || cambioProducto)) {

            throw new BusinessRuleException(
                    "No se puede cambiar la bodega o el producto mientras exista stock"
            );
        }
    }

    public void validarEliminar(InventarioBodega inventario) {
        if (inventario.getStock() > 0) {
            throw new BusinessRuleException(
                    "No se puede eliminar un inventario que todavía tiene unidades disponibles"
            );
        }
    }

    public void validarLimite(Integer limite) {
        if (limite == null || limite <= 0) {
            throw new BusinessRuleException(
                    "El límite de stock debe ser mayor que cero"
            );
        }
    }
}
