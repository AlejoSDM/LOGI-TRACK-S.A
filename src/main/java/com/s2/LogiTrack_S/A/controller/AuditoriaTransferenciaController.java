
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaTransferenciaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaTransferenciaDtoResponse;
import com.s2.LogiTrack_S.A.service.AuditoriaTransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditorias-transferencias")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auditoría de transferencias", description = "Registro y consulta de transferencias entre bodegas")
public class AuditoriaTransferenciaController {
    private final AuditoriaTransferenciaService auditoriaService;
    // REGISTRAR TRANSFERENCIA
    @Operation(summary = "Registrar una transferencia", description = "Traslada unidades de una bodega de origen a una bodega destino, actualiza ambos inventarios y registra la auditoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferencia registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, usuario sin permisos, stock insuficiente o capacidad superada"),
            @ApiResponse(responseCode = "404", description = "No existe el usuario, las bodegas, el producto o alguno de los inventarios")
    })
    @PostMapping
    public ResponseEntity<AuditoriaTransferenciaDtoResponse> guardar(@Valid @RequestBody AuditoriaTransferenciaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaService.guardar(dto));
    }

    // LISTAR TRANSFERENCIAS
    @Operation(summary = "Listar todas las transferencias", description = "Obtiene el historial completo de transferencias registradas.")
    @GetMapping
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> listar() {
        return ResponseEntity.ok(auditoriaService.listar());
    }

    // BUSCAR TRANSFERENCIA POR ID
    @Operation(
            summary = "Buscar transferencia por ID", description = "Obtiene una transferencia específica mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferencia encontrada"),
            @ApiResponse(responseCode = "404", description = "Transferencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaTransferenciaDtoResponse> buscarId(@Parameter(description = "Identificador de la transferencia") @PathVariable @Positive Long id) {
        return ResponseEntity.ok(auditoriaService.buscarId(id));
    }
}
