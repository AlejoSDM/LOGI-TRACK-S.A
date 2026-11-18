package com.s2.LogiTrack_S.A.repository;

import com.s2.LogiTrack_S.A.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUsername(String username);

    List<Usuario> findByRol(Usuario.Rol rol);

    List<Usuario> findByEstado(Boolean estado);

    List<Usuario> findByRolAndEstado(
            Usuario.Rol rol, Boolean estado);

    boolean existsByPersonaId_Id(Long personaId);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByPersonaId_IdAndIdNot(Long personaId, Long id);
}