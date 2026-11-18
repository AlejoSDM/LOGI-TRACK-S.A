
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.CiudadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CiudadValidation {

    private final CiudadRepository ciudadRepository;
    private final BodegaRepository bodegaRepository;

    public void validarNombreGuardar(String nombreCiudad) {
        if (ciudadRepository.existsByNombreCiudadIgnoreCase(nombreCiudad)) {
            throw new BusinessRuleException(
                    "Ya existe una ciudad con ese nombre"
            );
        }
    }

    public void validarNombreActualizar(String nombreCiudad, Long idCiudad) {
        if (ciudadRepository.existsByNombreCiudadIgnoreCaseAndIdCiudadNot(
                nombreCiudad, idCiudad)) {
            throw new BusinessRuleException(
                    "Ya existe otra ciudad con ese nombre"
            );
        }
    }

    public void validarEliminar(Long ciudadId) {
        if (bodegaRepository.existsByCiudadId_IdCiudad(ciudadId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una ciudad que tiene bodegas asociadas"
            );
        }
    }
}
