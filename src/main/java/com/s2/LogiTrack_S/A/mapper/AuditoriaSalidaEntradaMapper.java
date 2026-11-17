package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.Productos;
import com.s2.LogiTrack_S.A.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaSalidaEntradaMapper {
    public AuditoriaSalidaEntradaDtoResponse entityToDto(AuditoriaSalidaEntrada auditoriaSalidaEntrada, UsuarioDtoResponse usuarioDtoResponse, BodegaDtoResponse bodegaDtoResponse, ProductosDtoResponse productosDtoResponse) {
        if (auditoriaSalidaEntrada == null) return null;
        return new AuditoriaSalidaEntradaDtoResponse(
                auditoriaSalidaEntrada.getId(),
                auditoriaSalidaEntrada.getFecha(),
                auditoriaSalidaEntrada.getTipoAccion().name(),
                usuarioDtoResponse.id(),
                bodegaDtoResponse.id(),
                productosDtoResponse.id(),
                auditoriaSalidaEntrada.getCantidad()
        );
    }

    public AuditoriaSalidaEntrada dtoToEntity(AuditoriaSalidaEntradaDtoRequest dto, Usuario usuario, Bodega bodega, Productos productos){
        if(dto==null || usuario==null || bodega==null || productos==null) return null;
        AuditoriaSalidaEntrada auditoriaSalidaEntrada = new AuditoriaSalidaEntrada();
        auditoriaSalidaEntrada.setFecha(dto.fecha());
        auditoriaSalidaEntrada.setTipoAccion(dto.tipoAccion());
        auditoriaSalidaEntrada.setUsuarioId(usuario);
        auditoriaSalidaEntrada.setBodegaId(bodega);
        auditoriaSalidaEntrada.setProductoId(productos);
        auditoriaSalidaEntrada.setCantidad(dto.cantidad());
        return auditoriaSalidaEntrada;
    }

    public void updateEntityFromDto(AuditoriaSalidaEntrada auditoriaSalidaEntrada, AuditoriaSalidaEntradaDtoRequest dto, Usuario usuario, Bodega bodega, Productos productos){
        if(auditoriaSalidaEntrada==null || dto==null || usuario==null || bodega==null || productos==null) return;
        auditoriaSalidaEntrada.setFecha(dto.fecha());
        auditoriaSalidaEntrada.setTipoAccion(dto.tipoAccion());
        auditoriaSalidaEntrada.setUsuarioId(usuario);
        auditoriaSalidaEntrada.setBodegaId(bodega);
        auditoriaSalidaEntrada.setProductoId(productos);
        auditoriaSalidaEntrada.setCantidad(dto.cantidad());
    }
}
