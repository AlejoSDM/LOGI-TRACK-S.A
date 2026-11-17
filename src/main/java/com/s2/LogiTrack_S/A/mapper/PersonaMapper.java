package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.PersonaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import com.s2.LogiTrack_S.A.model.Persona;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {
    public PersonaDtoResponse entityToDto(Persona persona) {
        if (persona==null) return null;
        return new PersonaDtoResponse(
                persona.getId(),
                persona.getNombre());
    }

    public Persona dtoToEntity(PersonaDtoRequest dto) {
        if (dto==null) return null;
        Persona persona = new Persona();
        persona.setNombre(dto.nombre());
        return persona;
    }

    public void updateEntityFromDto(Persona persona, PersonaDtoRequest dto) {
        if (persona==null || dto==null) return;
        persona.setNombre(dto.nombre());
    }
}
