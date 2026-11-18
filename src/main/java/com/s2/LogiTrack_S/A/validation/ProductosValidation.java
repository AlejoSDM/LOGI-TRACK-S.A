
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.repository.AuditoriaSalidaEntradaRepository;
import com.s2.LogiTrack_S.A.repository.AuditoriaTransferenciaRepository;
import com.s2.LogiTrack_S.A.repository.InventarioBodegaRepository;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductosValidation {

    private final ProductosRepository productosRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final AuditoriaSalidaEntradaRepository auditoriaSalidaEntradaRepository;
    private final AuditoriaTransferenciaRepository auditoriaTransferenciaRepository;


    // VALIDAR NOMBRE AL GUARDAR

    public void validarNombreGuardar(String nombre, Long marcaId) {

        if (productosRepository.existsByNombreIgnoreCaseAndMarcaId_Id(
                nombre, marcaId)) {

            throw new BusinessRuleException(
                    "Ya existe un producto con ese nombre y esa marca"
            );
        }
    }


    // VALIDAR NOMBRE AL ACTUALIZAR

    public void validarNombreActualizar(String nombre, Long marcaId, Long id) {

        if (productosRepository.existsByNombreIgnoreCaseAndMarcaId_IdAndIdNot(
                nombre, marcaId, id)) {

            throw new BusinessRuleException(
                    "Ya existe otro producto con ese nombre y esa marca"
            );
        }
    }


    // VALIDAR ELIMINACIÓN

    public void validarEliminar(Long productoId) {

        if (inventarioBodegaRepository.existsByProductoId_Id(productoId)) {

            throw new BusinessRuleException(
                    "No se puede eliminar un producto que tiene inventario asociado"
            );
        }

        if (auditoriaSalidaEntradaRepository.existsByProductoId_Id(productoId)) {

            throw new BusinessRuleException(
                    "No se puede eliminar un producto que tiene movimientos registrados"
            );
        }

        if (auditoriaTransferenciaRepository.existsByProductoId_Id(productoId)) {

            throw new BusinessRuleException(
                    "No se puede eliminar un producto que tiene transferencias registradas"
            );
        }
    }
}
