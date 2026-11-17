package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.BodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Ciudad;
import com.s2.LogiTrack_S.A.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class BodegaMapper {
    public BodegaDtoResponse entityToDto(Bodega bodega, CiudadDtoResponse ciudad, UsuarioDtoResponse usuarioDtoResponse) {
        if (bodega == null || ciudad == null || usuarioDtoResponse == null) return null;
        return new BodegaDtoResponse(
                bodega.getId(),
                bodega.getNombre(),
                ciudad.idCiudad(),
                bodega.getCapacidad(),
                usuarioDtoResponse.id()
        );
    }

    public Bodega dtoToEntity(BodegaDtoRequest dto, Ciudad ciudad, Usuario usuario){
        if(dto==null || ciudad==null || usuario==null) return null;
        Bodega bodega = new Bodega();
        bodega.setNombre(dto.nombre());
        bodega.setCiudadId(ciudad);
        bodega.setCapacidad(dto.capacidad());
        bodega.setUsuarioId(usuario);
        return bodega;
    }

    public void updateEntityFromDto(Bodega bodega, BodegaDtoRequest dto, Ciudad ciudad, Usuario usuario){
        if(bodega==null || dto==null || ciudad==null || usuario==null) return;
        bodega.setNombre(dto.nombre());
        bodega.setCiudadId(ciudad);
        bodega.setCapacidad(dto.capacidad());
        bodega.setUsuarioId(usuario);
    }
}
