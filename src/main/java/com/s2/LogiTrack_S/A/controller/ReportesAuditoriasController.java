package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.ReportesAuditoriasDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.ReportesAuditoriasDtoResponse;
import com.s2.LogiTrack_S.A.service.ReportesAuditoriasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes-auditorias")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reportes de auditorías", description = "Gestión y consulta de reportes de auditorías")
public class ReportesAuditoriasController {

    private final ReportesAuditoriasService service;

    // GUARDAR REPORTE
    @Operation(summary = "Registrar un reporte de auditoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "No existe la auditoría asociada")
    })
    @PostMapping
    public ResponseEntity<ReportesAuditoriasDtoResponse> guardar(@Valid @RequestBody ReportesAuditoriasDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    // ACTUALIZAR REPORTE
    @Operation(summary = "Actualizar un reporte de auditoría")
    @PutMapping("/{id}")
    public ResponseEntity<ReportesAuditoriasDtoResponse> actualizar(@Parameter(description = "ID del reporte") @PathVariable @Positive Long id, @Valid @RequestBody ReportesAuditoriasDtoRequest dto) {
        return ResponseEntity.ok(service.actualizar(dto, id));
    }

    // ELIMINAR REPORTE
    @Operation(summary = "Eliminar un reporte de auditoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @Positive Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // LISTAR REPORTES
    @Operation(summary = "Listar todos los reportes")
    @GetMapping
    public ResponseEntity<List<ReportesAuditoriasDtoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // BUSCAR REPORTE POR ID
    @Operation(summary = "Buscar reporte por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReportesAuditoriasDtoResponse> buscarId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    // FILTRAR REPORTES POR FECHAS
    @Operation(summary = "Buscar reportes entre dos fechas")
    @GetMapping("/fecha")
    public ResponseEntity<List<ReportesAuditoriasDtoResponse>> buscarPorFecha(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime hasta) {

        return ResponseEntity.ok(service.buscarPorFecha(desde, hasta));
    }

    // FILTRAR REPORTES POR TRANSFERENCIA
    @Operation(summary = "Buscar reportes por ID de transferencia")
    @GetMapping("/transferencia/{transferenciaId}")
    public ResponseEntity<List<ReportesAuditoriasDtoResponse>> buscarPorTransferencia(@PathVariable @Positive Long transferenciaId) {
        return ResponseEntity.ok(service.buscarPorTransferencia(transferenciaId));
    }

    // FILTRAR REPORTES POR ENTRADA O SALIDA
    @Operation(summary = "Buscar reportes por ID de entrada/salida")
    @GetMapping("/salida-entrada/{salidaEntradaId}")
    public ResponseEntity<List<ReportesAuditoriasDtoResponse>> buscarPorSalidaEntrada(@PathVariable @Positive Long salidaEntradaId) {
        return ResponseEntity.ok(service.buscarPorSalidaEntrada(salidaEntradaId));
    }
}