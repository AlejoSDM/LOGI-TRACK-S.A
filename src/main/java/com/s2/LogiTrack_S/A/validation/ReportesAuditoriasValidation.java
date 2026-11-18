package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportesAuditoriasValidation {

    public void validarAuditoria(Long transferenciaId, Long salidaEntradaId) {

        boolean tieneTransferencia = transferenciaId != null;
        boolean tieneSalidaEntrada = salidaEntradaId != null;

        if (tieneTransferencia == tieneSalidaEntrada) {
            throw new BusinessRuleException(
                    "El reporte debe estar asociado a una transferencia o a una auditoría de entrada/salida, pero no a ambas"
            );
        }
    }

    public void validarFechas(LocalDateTime desde, LocalDateTime hasta) {

        if (desde == null || hasta == null) {
            throw new BusinessRuleException(
                    "Debe proporcionar una fecha inicial y una fecha final"
            );
        }

        if (desde.isAfter(hasta)) {
            throw new BusinessRuleException(
                    "La fecha inicial no puede ser posterior a la fecha final"
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
}