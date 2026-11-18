package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.UsuarioDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioDtoResponse guardar(UsuarioDtoRequest dto);
    UsuarioDtoResponse actualizar(UsuarioDtoRequest dto, Long id);
    void eliminar(Long id);
    List<UsuarioDtoResponse> listar();
    UsuarioDtoResponse buscarId(Long id);
    List<UsuarioDtoResponse> buscarPorRol(Usuario.Rol rol);
    List<UsuarioDtoResponse> buscarPorEstado(Boolean estado);
    List<UsuarioDtoResponse> buscarPorRolYEstado(Usuario.Rol rol, Boolean estado);
}

