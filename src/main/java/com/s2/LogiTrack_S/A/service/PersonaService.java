package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.PersonaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import java.util.List;

public interface PersonaService {

    PersonaDtoResponse guardar(PersonaDtoRequest dto);
    PersonaDtoResponse actualizar(PersonaDtoRequest dto, Long id);
    void eliminar(Long id);
    List<PersonaDtoResponse> listar();
    PersonaDtoResponse buscarId(Long id);
}
