package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    boolean existsByNombreCiudadIgnoreCase(String nombreCiudad);

    boolean existsByNombreCiudadIgnoreCaseAndIdCiudadNot(
            String nombreCiudad, Long idCiudad);
}