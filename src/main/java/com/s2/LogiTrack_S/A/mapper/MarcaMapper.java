package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.MarcaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.model.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {
    public MarcaDtoResponse entityToDto(Marca marca) {
        if (marca == null) return null;
        return new MarcaDtoResponse(
                marca.getId(),
                marca.getNombreMarca()
        );
    }

    public Marca dtoToEntity(MarcaDtoRequest dto){
        if(dto==null) return null;
        Marca marca = new Marca();
        marca.setNombreMarca(dto.nombreMarca());
        return marca;
    }

    public void updateEntityFromDto(Marca marca, MarcaDtoRequest dto){
        if(marca==null || dto==null) return;
        marca.setNombreMarca(dto.nombreMarca());
    }
}
