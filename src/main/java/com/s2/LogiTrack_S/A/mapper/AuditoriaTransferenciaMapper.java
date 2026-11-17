package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.AuditoriaTransferencia;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaTransferenciaMapper {
    public AuditoriaTransferenciaDtoResponse entityToDto(AuditoriaTransferencia AuditoriaT, UsuarioDtoResponse usuarioDtoResponse,
                                                         BodegaDtoResponse bodegaOrigenDto, BodegaDtoResponse bodegaDestinoDto, ProductosDtoResponse productosDtoResponse) {
        if(AuditoriaT == null || usuarioDtoResponse == null || bodegaOrigenDto == null || bodegaDestinoDto == null || productosDtoResponse == null) return null;
        return new AuditoriaTransferenciaDtoResponse(
                AuditoriaT.getId(),
                AuditoriaT.getFecha(),
                usuarioDtoResponse.id(),
                bodegaOrigenDto.id(),
                bodegaDestinoDto.id(),
                productosDtoResponse.id(),
                AuditoriaT.getCantidad()
        );
    }

    public AuditoriaTransferencia dtoToEntity(AuditoriaTransferenciaDtoRequest dto, Usuario usuario, Bodega bodegaOrigen, Bodega bodegaDestino, Productos productos){
        if(dto==null || usuario==null || bodegaOrigen==null || bodegaDestino==null || productos==null) return null;
        AuditoriaTransferencia auditoriaTransferencia = new AuditoriaTransferencia();
        auditoriaTransferencia.setFecha(dto.fecha());
        auditoriaTransferencia.setUsuarioId(usuario);
        auditoriaTransferencia.setBodegaOrigenId(bodegaOrigen);
        auditoriaTransferencia.setBodegaDestinoId(bodegaDestino);
        auditoriaTransferencia.setProductoId(productos);
        auditoriaTransferencia.setCantidad(dto.cantidad());
        return auditoriaTransferencia;
    }

    public void updateEntityFromDto(AuditoriaTransferencia auditoriaTransferencia, AuditoriaTransferenciaDtoRequest dto, Usuario usuario, Bodega bodegaOrigen, Bodega bodegaDestino, Productos productos){
        if(auditoriaTransferencia==null || dto==null || usuario==null || bodegaOrigen==null || bodegaDestino==null || productos==null) return;
        auditoriaTransferencia.setFecha(dto.fecha());
        auditoriaTransferencia.setUsuarioId(usuario);
        auditoriaTransferencia.setBodegaOrigenId(bodegaOrigen);
        auditoriaTransferencia.setBodegaDestinoId(bodegaDestino);
        auditoriaTransferencia.setProductoId(productos);
        auditoriaTransferencia.setCantidad(dto.cantidad());
    }
}
