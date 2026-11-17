package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.InventarioBodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioBodegaRepository extends JpaRepository<InventarioBodega, Long> {
    List<InventarioBodega> findByStockLessThan(Integer limite);
    List<InventarioBodega> findByBodegaId_Id(Long bodegaId);
    List<InventarioBodega> findByProductoId_Id(Long productoId);
    Optional<InventarioBodega> findByBodegaId_IdAndProductoId_Id(Long bodegaId, Long productoId);
}
