package com.s2.LogiTrack_S.A.service;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import java.util.List;

public interface AuditoriaTransferenciaService {

    AuditoriaTransferenciaDtoResponse guardar(AuditoriaTransferenciaDtoRequest dto);
    List<AuditoriaTransferenciaDtoResponse> listar();
    AuditoriaTransferenciaDtoResponse buscarId(Long id);
}
