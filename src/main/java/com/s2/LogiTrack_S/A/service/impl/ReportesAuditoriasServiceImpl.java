
package com.s2.LogiTrack_S.A.service.impl;

import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import com.s2.LogiTrack_S.A.exception.DatabaseRuleException;
import com.s2.LogiTrack_S.A.mapper.ReportesAuditoriasMapper;
import com.s2.LogiTrack_S.A.model.ReportesAuditorias;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.ReportesAuditoriasRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import com.s2.LogiTrack_S.A.validation.ReportesAuditoriasValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportesAuditoriasServiceImpl
        implements ReportesAuditoriasService {

    private final ReportesAuditoriasRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ReportesAuditoriasMapper mapper;
    private final ReportesAuditoriasValidation validation;

    @Override
    @Transactional
    public void registrar(String modulo, String tipoAccion, Long registroId, String descripcion
    ) {
        validation.validarRegistro(modulo, tipoAccion, descripcion);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new DatabaseRuleException("No se pudo identificar al usuario que realizó la acción");
        }
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName()).orElseThrow(() -> new DatabaseRuleException("No existe el usuario autenticado"));
        ReportesAuditorias reporte = new ReportesAuditorias();
        reporte.setUsuarioId(usuario);
        reporte.setModulo(modulo);
        reporte.setTipoAccion(tipoAccion);
        reporte.setRegistroId(registroId);
        reporte.setDescripcion(descripcion);
        repository.save(reporte);
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> listar() {

        return repository.findAllByOrderByFechaDesc()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public ReportesAuditoriasDtoResponse buscarId(Long id) {

        validation.validarId(id);

        ReportesAuditorias reporte = repository.findById(id)
                .orElseThrow(() -> new DatabaseRuleException(
                        "No existe el reporte de auditoría"
                ));

        return mapper.entityToDto(reporte);
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> buscarPorModulo(
            String modulo
    ) {

        if (modulo == null || modulo.isBlank()) {
            throw new DatabaseRuleException(
                    "Debe proporcionar un módulo"
            );
        }

        return repository
                .findByModuloOrderByFechaDesc(modulo.toUpperCase())
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> buscarPorTipoAccion(
            String tipoAccion
    ) {

        if (tipoAccion == null || tipoAccion.isBlank()) {
            throw new DatabaseRuleException(
                    "Debe proporcionar un tipo de acción"
            );
        }

        return repository
                .findByTipoAccionOrderByFechaDesc(tipoAccion.toUpperCase())
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> buscarPorFecha(
            LocalDateTime desde,
            LocalDateTime hasta
    ) {

        validation.validarFechas(desde, hasta);

        return repository
                .findByFechaBetweenOrderByFechaDesc(desde, hasta)
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    @Override
    public List<ReportesAuditoriasDtoResponse> buscarPorUsuario(
            Long usuarioId
    ) {

        validation.validarId(usuarioId);

        return repository
                .findByUsuarioId_IdOrderByFechaDesc(usuarioId)
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }
}
