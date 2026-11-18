package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.InventarioBodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.InventarioBodegaDtoResponse;
import java.util.List;

public interface InventarioBodegaService {
    InventarioBodegaDtoResponse guardar(InventarioBodegaDtoRequest dto);
    InventarioBodegaDtoResponse actualizar(InventarioBodegaDtoRequest dto, Long id);
    void eliminar(Long id);
    List<InventarioBodegaDtoResponse> listar();
    InventarioBodegaDtoResponse buscarId(Long id);
    List<InventarioBodegaDtoResponse> buscarBodega(Long bodegaId);
    List<InventarioBodegaDtoResponse> buscarProducto(Long productoId);
    List<InventarioBodegaDtoResponse> buscarStockBajo(Integer limite);
    Long sumarStockPorBodega(Long bodegaId);
}
