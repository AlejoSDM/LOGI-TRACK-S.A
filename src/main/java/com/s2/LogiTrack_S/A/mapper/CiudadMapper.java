package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.CiudadDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import com.s2.LogiTrack_S.A.model.Ciudad;
import org.springframework.stereotype.Component;

@Component
public class CiudadMapper {
    public CiudadDtoResponse entityToDto(Ciudad ciudad) {
        if (ciudad == null) return null;
        return new CiudadDtoResponse(
                ciudad.getIdCiudad(),
                ciudad.getNombreCiudad()
        );
    }

    public Ciudad dtoToEntity(CiudadDtoRequest dto) {
        if (dto == null) return null;
        Ciudad ciudad = new Ciudad();
        ciudad.setNombreCiudad(dto.nombreCiudad());
        return ciudad;
    }

    public void updateEntityFromDto(Ciudad ciudad, CiudadDtoRequest dto) {
        if (ciudad == null || dto == null) return;
        ciudad.setNombreCiudad(dto.nombreCiudad());
    }
}
