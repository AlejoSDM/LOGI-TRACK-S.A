package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.AuditoriaSalidaEntradaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.AuditoriaSalidaEntradaDtoResponse;
import com.s2.LogiTrack_S.A.model.AuditoriaSalidaEntrada;
import com.s2.LogiTrack_S.A.service.AuditoriaSalidaEntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // BUSCAR POR RANGO DE FECHAS
    @Operation(summary = "Buscar entradas y salidas por rango de fechas")
    @GetMapping("/fecha")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(auditoriaService.buscarPorFecha(desde, hasta));
    }

    // BUSCAR POR USUARIO
    @Operation(summary = "Buscar entradas y salidas por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorUsuario(@PathVariable @Positive Long usuarioId) {
        return ResponseEntity.ok(auditoriaService.buscarPorUsuario(usuarioId));
    }

    // BUSCAR POR TIPO DE ACCIÓN
    @Operation(summary = "Buscar entradas o salidas por tipo de acción")
    @GetMapping("/tipo/{tipoAccion}")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorTipoAccion(@PathVariable AuditoriaSalidaEntrada.TipoAccion tipoAccion) {
        return ResponseEntity.ok(auditoriaService.buscarPorTipoAccion(tipoAccion));
    }

    // BUSCAR POR USUARIO Y FECHAS
    @Operation(summary = "Buscar entradas y salidas por usuario y rango de fechas")
    @GetMapping("/usuario/{usuarioId}/fecha")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorUsuarioYFecha(@PathVariable @Positive Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(auditoriaService.buscarPorUsuarioYFecha(usuarioId, desde, hasta));
    }

    // BUSCAR POR BODEGA
    @Operation(summary = "Buscar entradas y salidas por bodega")
    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorBodega(@PathVariable @Positive Long bodegaId) {
        return ResponseEntity.ok(auditoriaService.buscarPorBodega(bodegaId));
    }

    // BUSCAR POR PRODUCTO
    @Operation(summary = "Buscar entradas y salidas por producto")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorProducto(@PathVariable @Positive Long productoId) {
        return ResponseEntity.ok(auditoriaService.buscarPorProducto(productoId));
    }

    // BUSCAR POR BODEGA Y FECHAS
    @Operation(summary = "Buscar entradas y salidas por bodega y rango de fechas")
    @GetMapping("/bodega/{bodegaId}/fecha")
    public ResponseEntity<List<AuditoriaSalidaEntradaDtoResponse>> buscarPorBodegaYFecha(@PathVariable @Positive Long bodegaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(auditoriaService.buscarPorBodegaYFecha(bodegaId, desde, hasta)
        );
    }
}