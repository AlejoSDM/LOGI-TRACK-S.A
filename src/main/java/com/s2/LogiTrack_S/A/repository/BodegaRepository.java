package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    List<Bodega> findByNombreContainingIgnoreCase(String nombre);

    List<Bodega> findByCiudadId(Long ciudadId);

    List<Bodega> findByUsuarioId_Id(Long usuarioId);

    boolean existsByNombreIgnoreCaseAndCiudadId_IdCiudad(
            String nombre, Long ciudadId);

    boolean existsByNombreIgnoreCaseAndCiudadId_IdCiudadAndIdNot(
            String nombre, Long ciudadId, Long id);

    boolean existsByCiudadId_IdCiudad(Long ciudadId);
}