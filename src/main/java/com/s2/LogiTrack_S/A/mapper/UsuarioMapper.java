package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.UsuarioDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.Persona;
import com.s2.LogiTrack_S.A.model.Usuario;
import org.springframework.stereotype.Component;

@Component

public class UsuarioMapper {
    public UsuarioDtoResponse entityToDto(Usuario usuario, PersonaDtoResponse personaDtoResponse) {
        if (usuario == null) return null;
        return new UsuarioDtoResponse(usuario.getId(),
                personaDtoResponse.id(),
                usuario.getUsername(),
                usuario.getEstado());
    }

    public Usuario DtoToEntity(UsuarioDtoRequest dto, Persona persona) {
        if(dto==null || persona == null) return null;
        Usuario usuario = new Usuario();
        usuario.setPersonaId(persona);
        usuario.setUsername(dto.username());
        usuario.setContraseña(dto.contraseña());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());
        return usuario;
    }

    public void updateEntityFromDto(Usuario usuario, UsuarioDtoRequest dto, Persona persona) {
        if(usuario==null || dto==null) return;
        usuario.setPersonaId(persona);
        usuario.setUsername(dto.username());
        usuario.setContraseña(dto.contraseña());
        usuario.setRol(dto.rol());
        usuario.setEstado(dto.estado());
    }

}
