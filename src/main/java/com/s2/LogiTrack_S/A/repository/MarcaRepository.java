package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findByNombreMarcaContainingIgnoreCase(String nombreMarca);

    boolean existsByNombreMarcaIgnoreCase(String nombreMarca);

    boolean existsByNombreMarcaIgnoreCaseAndIdNot(
            String nombreMarca, Long id);
}