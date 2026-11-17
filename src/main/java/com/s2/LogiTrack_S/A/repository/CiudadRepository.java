package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByNombreCiudadContainingIgnoreCase(String nombreCiudad);
}
