
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class ReportesAuditoriasValidation {

    private static final Set<String> MODULOS = Set.of(
            "PRODUCTO",
            "MARCA",
            "CATEGORIA",
            "BODEGA",
            "CIUDAD",
            "INVENTARIO"
    );

    private static final Set<String> ACCIONES = Set.of(
            "CREAR",
            "MODIFICAR",
            "ELIMINAR",
            "ENTRADA",
            "SALIDA",
            "TRANSFERENCIA",
            "ACTIVAR",
            "DESACTIVAR"
    );

    public void validarRegistro(
            String modulo,
            String tipoAccion,
            String descripcion
    ) {

        if (modulo == null || !MODULOS.contains(modulo)) {
            throw new BusinessRuleException(
                    "El módulo de auditoría no es válido"
            );
        }

        if (tipoAccion == null || !ACCIONES.contains(tipoAccion)) {
            throw new BusinessRuleException(
                    "El tipo de acción no es válido"
            );
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new BusinessRuleException(
                    "La descripción no puede estar vacía"
            );
        }

        if (descripcion.length() > 500) {
            throw new BusinessRuleException(
                    "La descripción no puede superar los 500 caracteres"
            );
        }
    }

    public void validarId(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "El ID debe ser un número positivo"
            );
        }
    }

    public void validarFechas(
            LocalDateTime desde,
            LocalDateTime hasta
    ) {

        if (desde == null || hasta == null) {
            throw new BusinessRuleException(
                    "Debe proporcionar ambas fechas"
            );
        }

        if (desde.isAfter(hasta)) {
            throw new BusinessRuleException(
                    "La fecha inicial no puede ser posterior a la final"
            );
        }
    }
}
