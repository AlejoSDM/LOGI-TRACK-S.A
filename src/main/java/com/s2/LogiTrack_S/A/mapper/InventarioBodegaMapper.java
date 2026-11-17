package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.InventarioBodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.InventarioBodegaDtoResponse;
import com.s2.LogiTrack_S.A.model.Bodega;
import com.s2.LogiTrack_S.A.model.InventarioBodega;
import com.s2.LogiTrack_S.A.model.Productos;
import org.springframework.stereotype.Component;

@Component
public class InventarioBodegaMapper {
    public InventarioBodegaDtoResponse entityToDto(InventarioBodega inventarioBodega) {
        if (inventarioBodega == null) return null;
        return new InventarioBodegaDtoResponse(
                inventarioBodega.getId(),
                inventarioBodega.getBodegaId().getId(),
                inventarioBodega.getProductoId().getId(),
                inventarioBodega.getStock()
        );
    }

    public InventarioBodega dtoToEntity(InventarioBodegaDtoRequest dto, Bodega bodega, Productos productos){
        if(dto==null || bodega==null || productos==null) return null;
        InventarioBodega inventarioBodega = new InventarioBodega();
        inventarioBodega.setBodegaId(bodega);
        inventarioBodega.setProductoId(productos);
        inventarioBodega.setStock(dto.stock());
        return inventarioBodega;
    }

    public void updateEntityFromDto(InventarioBodega inventarioBodega, InventarioBodegaDtoRequest dto, Bodega bodega, Productos productos){
        if(inventarioBodega==null || dto==null || bodega==null || productos==null) return;
        inventarioBodega.setBodegaId(bodega);
        inventarioBodega.setProductoId(productos);
        inventarioBodega.setStock(dto.stock());
    }
}
