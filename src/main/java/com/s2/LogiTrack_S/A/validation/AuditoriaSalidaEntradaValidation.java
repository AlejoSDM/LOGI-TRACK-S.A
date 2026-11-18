package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.InventarioBodega;
import com.s2.LogiTrack_S.A.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditoriaSalidaEntradaValidation {

    public void validarUsuario(Usuario usuario, Bodega bodega) {

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

        if (!bodega.getUsuarioId().getId().equals(usuario.getId())) {
            throw new BusinessRuleException(
                    "El usuario no es el encargado de esta bodega"
            );
        }
    }

    public void validarStock(
            InventarioBodega inventario,
            Integer cantidad
    ) {

        if (inventario.getStock() < cantidad) {
            throw new BusinessRuleException(
                    "No hay suficiente stock para realizar esta salida"
            );
        }
    }

    public void modificarStock(InventarioBodega inventario,
                               AuditoriaSalidaEntrada.TipoAccion tipoAccion,
                               Integer cantidad) {

        if (tipoAccion == AuditoriaSalidaEntrada.TipoAccion.ENTRADA) {

            inventario.setStock(Math.addExact(inventario.getStock(), cantidad));

        } else if (tipoAccion == AuditoriaSalidaEntrada.TipoAccion.SALIDA) {

            validarStock(inventario, cantidad);

            inventario.setStock(inventario.getStock() - cantidad);
        }
    }
}