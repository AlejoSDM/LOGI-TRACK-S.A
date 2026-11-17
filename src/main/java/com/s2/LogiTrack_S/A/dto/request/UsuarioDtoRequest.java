package com.s2.LogiTrack_S.A.dto.request;

import com.s2.LogiTrack_S.A.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UsuarioDtoRequest(

        @NotNull(message = "El ID de la persona no puede ser nulo")
        @Positive (message = "El ID de la persona debe ser un número positivo")
        Long personaId,

        @NotBlank(message = "El nombre de usuario no puede estar vacio")
        @Size(min = 6, max = 50, message = "El nombre de usuario debe tener entre 6 y 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacia")
        @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
        String contraseña,

        @NotNull(message = "El rol no puede ser nulo")
        Usuario.Rol rol,

        @NotNull(message = "El estado no puede ser nulo")
        Boolean estado
) {
}
