package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.service.AuditoriaSalidaEntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias-salida-entrada")
@RequiredArgsConstructor
@Tag(name = "Auditoría de entradas y salidas", description = "Registro y consulta de movimientos de inventario")
public class AuditoriaSalidaEntradaController {

    private final AuditoriaSalidaEntradaService auditoriaService;

    // REGISTRAR ENTRADA O SALIDA
    @Operation(summary = "Registrar una entrada o salida", description = "Registra el movimiento y actualiza automáticamente el stock.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento registrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, usuario sin permisos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "No existe el usuario, bodega, producto o inventario")
    })
    @PostMapping
    public ResponseEntity<AuditoriaSalidaEntradaDtoResponse> guardar(@Valid @RequestBody AuditoriaSalidaEntradaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(auditoriaService.guardar(dto));
    }

    // LISTAR AUDITORÍAS
    @Operation(summary = "Listar todas las entradas y salidas")
    @GetMapping
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> listar() {
        return ResponseEntity.ok(auditoriaService.listar());
    }

    // BUSCAR POR ID
    @Operation(summary = "Buscar una auditoría por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaSalidaEntradaDtoResponse> buscarId(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.buscarId(id));
    }
}