
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes-auditorias")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reportes de auditorías", description = "Consulta general de las acciones realizadas en el sistema")
public class ReportesAuditoriasController {
    private final ReportesAuditoriasService service;

    @Operation(summary = "Listar todos los reportes")
    @GetMapping
    public List<ReportesAuditoriasDtoResponse> listar() {return service.listar();}

    @Operation(summary = "Buscar reporte por ID")
    @GetMapping("/{id}")
    public ReportesAuditoriasDtoResponse buscarId(@PathVariable @Positive Long id
    ) {
        return service.buscarId(id);
    }

    @Operation(summary = "Buscar reportes por módulo")
    @GetMapping("/modulo/{modulo}")
    public List<ReportesAuditoriasDtoResponse> buscarPorModulo(@PathVariable String modulo
    ) {
        return service.buscarPorModulo(modulo);
    }

    @Operation(summary = "Buscar reportes por tipo de acción")
    @GetMapping("/accion/{tipoAccion}")
    public List<ReportesAuditoriasDtoResponse> buscarPorTipoAccion(
            @PathVariable String tipoAccion
    ) {
        return service.buscarPorTipoAccion(tipoAccion);
    }

    @Operation(summary = "Buscar reportes por fechas")
    @GetMapping("/fecha")
    public List<ReportesAuditoriasDtoResponse> buscarPorFecha(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime hasta

    ) {
        return service.buscarPorFecha(desde, hasta);
    }

    @Operation(summary = "Buscar reportes por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public List<ReportesAuditoriasDtoResponse> buscarPorUsuario(
            @PathVariable @Positive Long usuarioId
    ) {
        return service.buscarPorUsuario(usuarioId);
    }
}
