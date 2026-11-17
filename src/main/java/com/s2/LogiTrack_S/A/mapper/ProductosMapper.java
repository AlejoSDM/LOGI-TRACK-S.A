package com.s2.LogiTrack_S.A.mapper;

import com.s2.LogiTrack_S.A.dto.request.ProductosDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import com.s2.LogiTrack_S.A.model.Marca;
import com.s2.LogiTrack_S.A.model.Productos;
import org.springframework.stereotype.Component;

@Component
public class ProductosMapper {
    public ProductosDtoResponse entityToDto (Productos productos, MarcaDtoResponse marcaDtoResponse, CategoriaProductoDtoResponse categoriaProductoDtoResponse) {
        if (productos==null || marcaDtoResponse==null || categoriaProductoDtoResponse==null) return null;
        return new ProductosDtoResponse(
                productos.getId(),
                productos.getNombre(),
                marcaDtoResponse.id(),
                categoriaProductoDtoResponse.id(),
                productos.getPrecioUnitario()
        );
    }

    public Productos dtoToEntity (ProductosDtoRequest dto, Marca marca, CategoriaProducto categoriaProducto){
        if(dto==null || marca==null || categoriaProducto==null) return null;
        Productos productos=new Productos();
        productos.setNombre(dto.nombre());
        productos.setMarcaId(marca);
        productos.setCategoriaId(categoriaProducto);
        productos.setPrecioUnitario(dto.precioUnitario());
        return productos;
    }

    public void updateEntityFromDto(Productos productos, ProductosDtoRequest dto, Marca marca, CategoriaProducto categoriaProducto){
        if(productos==null || dto==null || marca==null || categoriaProducto==null) return;
        productos.setNombre(dto.nombre());
        productos.setMarcaId(marca);
        productos.setCategoriaId(categoriaProducto);
        productos.setPrecioUnitario(dto.precioUnitario());
    }

}
