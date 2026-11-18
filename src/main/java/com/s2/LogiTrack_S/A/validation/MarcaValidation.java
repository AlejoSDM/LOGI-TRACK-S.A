
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.repository.MarcaRepository;
import com.s2.LogiTrack_S.A.repository.ProductosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarcaValidation {

    private final MarcaRepository marcaRepository;
    private final ProductosRepository productosRepository;

    public void validarNombreGuardar(String nombreMarca) {
        if (marcaRepository.existsByNombreMarcaIgnoreCase(nombreMarca)) {
            throw new BusinessRuleException(
                    "Ya existe una marca con ese nombre"
            );
        }
    }

    public void validarNombreActualizar(String nombreMarca, Long id) {
        if (marcaRepository.existsByNombreMarcaIgnoreCaseAndIdNot(
                nombreMarca, id)) {
            throw new BusinessRuleException(
                    "Ya existe otra marca con ese nombre"
            );
        }
    }

    public void validarEliminar(Long marcaId) {
        if (productosRepository.existsByMarcaId_Id(marcaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una marca que tiene productos asociados"
            );
        }
    }
}
