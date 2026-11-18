package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.AuditoriaSalidaEntradaRepository;
import com.s2.LogiTrack_S.A.repository.AuditoriaTransferenciaRepository;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.InventarioBodegaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BodegaValidation {

    private final BodegaRepository bodegaRepository;
    private final InventarioBodegaRepository inventarioBodegaRepository;
    private final AuditoriaSalidaEntradaRepository auditoriaSalidaEntradaRepository;
    private final AuditoriaTransferenciaRepository auditoriaTransferenciaRepository;

    public void validarNombreGuardar(String nombre, Long ciudadId) {
        if (bodegaRepository.existsByNombreIgnoreCaseAndCiudadId_IdCiudad(nombre, ciudadId)) {
            throw new BusinessRuleException(
                    "Ya existe una bodega con ese nombre en esta ciudad"
            );
        }
    }

    public void validarNombreActualizar(String nombre, Long ciudadId, Long id) {
        if (bodegaRepository.existsByNombreIgnoreCaseAndCiudadId_IdCiudadAndIdNot(
                nombre, ciudadId, id)) {
            throw new BusinessRuleException(
                    "Ya existe otra bodega con ese nombre en esta ciudad"
            );
        }
    }

    public void validarCapacidad(Integer capacidad, Long bodegaId) {
        if (capacidad == null || capacidad <= 0) {
            throw new BusinessRuleException(
                    "La capacidad debe ser mayor que cero"
            );
        }

        if (bodegaId != null) {
            Long stockActual = inventarioBodegaRepository.sumarStockPorBodega(bodegaId);

            if (capacidad < stockActual) {
                throw new BusinessRuleException(
                        "La capacidad no puede ser menor al inventario actual"
                );
            }
        }
    }

    public void validarEmpleado(Usuario usuario) {
        if (!Boolean.TRUE.equals(usuario.getEstado())) {
            throw new BusinessRuleException(
                    "El empleado encargado debe estar activo"
            );
        }

        if (usuario.getRol() == null || !"EMPLEADO".equals(usuario.getRol().name())) {
            throw new BusinessRuleException(
                    "El encargado de la bodega debe tener el rol EMPLEADO"
            );
        }
    }

    public void validarEliminar(Long bodegaId) {

        if (inventarioBodegaRepository.existsByBodegaId_Id(bodegaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una bodega que tiene inventario asociado"
            );
        }

        if (auditoriaSalidaEntradaRepository.existsByBodegaId_Id(bodegaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una bodega que tiene movimientos registrados"
            );
        }

        if (auditoriaTransferenciaRepository
                .existsByBodegaOrigenId_IdOrBodegaDestinoId_Id(bodegaId, bodegaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una bodega que tiene transferencias registradas"
            );
        }
    }
}