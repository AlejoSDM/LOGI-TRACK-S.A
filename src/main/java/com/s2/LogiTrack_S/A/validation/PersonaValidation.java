
package com.s2.LogiTrack_S.A.validation;

import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonaValidation {

    private final UsuarioRepository usuarioRepository;

    public void validarEliminar(Long personaId) {
        if (usuarioRepository.existsByPersonaId_Id(personaId)) {
            throw new BusinessRuleException(
                    "No se puede eliminar una persona que tiene un usuario asociado"
            );
        }
    }
}
