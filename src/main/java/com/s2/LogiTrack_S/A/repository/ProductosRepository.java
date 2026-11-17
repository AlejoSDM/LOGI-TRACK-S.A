package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    List<Productos> findByNombreContainingIgnoreCase(String nombre);
    List<Productos> findByCategoriaId_Id(Long categoriaId);
    List<Productos> findByMarcaId_Id(Long marcaId);
}
