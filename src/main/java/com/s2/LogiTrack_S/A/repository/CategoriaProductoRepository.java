package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
    boolean existsByTipoCategoria(CategoriaProducto.TipoCategoria tipoCategoria);
}
