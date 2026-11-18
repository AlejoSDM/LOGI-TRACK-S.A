
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.repository.AuditoriaSalidaEntradaRepository;
import com.s2.LogiTrack_S.A.repository.AuditoriaTransferenciaRepository;
import com.s2.LogiTrack_S.A.repository.BodegaRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidation {

    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final AuditoriaSalidaEntradaRepository auditoriaSalidaEntradaRepository;
    private final AuditoriaTransferenciaRepository auditoriaTransferenciaRepository;

    public void validarGuardar(String username, Long personaId) {

        if (usuarioRepository.existsByUsername(username)) {
            throw new BusinessRuleException(
                    "El nombre de usuario ya está registrado"
            );
        }

        if (usuarioRepository.existsByPersonaId_Id(personaId)) {
            throw new BusinessRuleException(
                    "Esta persona ya tiene una cuenta de usuario"
            );
        }
    }

    public void validarActualizar(String username, Long personaId, Long id) {

        if (usuarioRepository.existsByUsernameAndIdNot(username, id)) {
            throw new BusinessRuleException(
                    "El nombre de usuario ya está registrado"
            );
        }

        if (usuarioRepository.existsByPersonaId_IdAndIdNot(personaId, id)) {
            throw new BusinessRuleException(
                    "Esta persona ya tiene una cuenta de usuario"
            );
        }
    }

    public void validarEliminar(Long usuarioId) {

        if (bodegaRepository.existsByUsuarioId_Id(usuarioId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar un usuario que está asignado a una bodega. Primero reasigne la bodega."
            );
        }

        if (auditoriaSalidaEntradaRepository.existsByUsuarioId_Id(usuarioId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar un usuario que tiene movimientos registrados en auditoría"
            );
        }

        if (auditoriaTransferenciaRepository.existsByUsuarioId_Id(usuarioId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar un usuario que tiene transferencias registradas en auditoría"
            );
        }
    }
}
