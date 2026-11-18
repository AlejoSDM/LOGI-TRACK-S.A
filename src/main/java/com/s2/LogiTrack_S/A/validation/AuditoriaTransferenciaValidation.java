
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.InventarioBodega;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.InventarioBodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuditoriaTransferenciaValidation {

    private final InventarioBodegaRepository inventarioBodegaRepository;

    // VALIDAR USUARIO RESPONSABLE
    public void validarUsuario(Usuario usuario, Bodega origen) {

        if (!Boolean.TRUE.equals(usuario.getEstado())) {
            throw new BusinessRuleException(
                    "El usuario responsable se encuentra inactivo"
            );
        }

        if (usuario.getRol() != Usuario.Rol.EMPLEADO) {
            throw new BusinessRuleException(
                    "El usuario responsable debe ser un empleado"
            );
        }

        if (!origen.getUsuarioId().getId().equals(usuario.getId())) {
            throw new BusinessRuleException(
                    "El usuario no es el encargado de la bodega de origen"
            );
        }
    }

    // VALIDAR QUE LAS BODEGAS SEAN DIFERENTES
    public void validarBodegas(Long origenId, Long destinoId) {

        if (origenId.equals(destinoId)) {
            throw new BusinessRuleException(
                    "La bodega de origen y destino no pueden ser iguales"
            );
        }
    }

    // VALIDAR STOCK DISPONIBLE
    public void validarStock(InventarioBodega inventario, Integer cantidad) {

        if (inventario.getStock() < cantidad) {
            throw new BusinessRuleException(
                    "No hay suficiente stock en la bodega de origen"
            );
        }
    }

    // VALIDAR CAPACIDAD DE LA BODEGA DESTINO
    public void validarCapacidad(Bodega destino, Integer cantidad) {

        Long stockActual =
                inventarioBodegaRepository.sumarStockPorBodega(destino.getId());

        long stockFinal = stockActual + cantidad.longValue();

        if (stockFinal > destino.getCapacidad()) {
            throw new BusinessRuleException(
                    "La transferencia supera la capacidad disponible de la bodega destino"
            );
        }
    }

    // MODIFICAR STOCK DE AMBAS BODEGAS
    public void modificarStock(
            InventarioBodega inventarioOrigen,
            InventarioBodega inventarioDestino,
            Integer cantidad
    ) {

        inventarioOrigen.setStock(
                inventarioOrigen.getStock() - cantidad
        );

        inventarioDestino.setStock(
                Math.addExact(inventarioDestino.getStock(), cantidad)
        );
    }

    public void validarCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new BusinessRuleException(
                    "La cantidad a transferir debe ser mayor que cero"
            );
        }
    }

    // VALIDAR ID DE LOS FILTROS
    public void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "El ID debe ser un número positivo"
            );
        }
    }

    // VALIDAR RANGO DE FECHAS
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
}
