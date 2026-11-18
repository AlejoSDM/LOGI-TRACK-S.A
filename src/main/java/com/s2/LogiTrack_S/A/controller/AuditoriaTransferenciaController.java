
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // BUSCAR POR RANGO DE FECHAS
    @Operation(summary = "Buscar transferencias por rango de fechas")
    @GetMapping("/fecha")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(auditoriaService.buscarPorFecha(desde, hasta));
    }

    // BUSCAR POR USUARIO
    @Operation(summary = "Buscar transferencias por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorUsuario(@PathVariable @Positive Long usuarioId) {
        return ResponseEntity.ok(auditoriaService.buscarPorUsuario(usuarioId));
    }

    // BUSCAR POR BODEGA DE ORIGEN O DESTINO
    @Operation(summary = "Buscar transferencias donde participa una bodega")
    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorBodegas(@PathVariable @Positive Long bodegaId) {
        return ResponseEntity.ok(auditoriaService.buscarPorBodegas(bodegaId));
    }

    // BUSCAR POR USUARIO Y FECHAS
    @Operation(summary = "Buscar transferencias por usuario y rango de fechas")
    @GetMapping("/usuario/{usuarioId}/fecha")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorUsuarioYFecha(@PathVariable @Positive Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        return ResponseEntity.ok(auditoriaService.buscarPorUsuarioYFecha(usuarioId, desde, hasta)
        );
    }

    // BUSCAR POR PRODUCTO
    @Operation(summary = "Buscar transferencias por producto")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorProducto(@PathVariable @Positive Long productoId) {
        return ResponseEntity.ok(auditoriaService.buscarPorProducto(productoId));
    }

    // BUSCAR POR BODEGA DE ORIGEN
    @Operation(summary = "Buscar transferencias por bodega de origen")
    @GetMapping("/bodega-origen/{bodegaId}")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorBodegaOrigen(@PathVariable @Positive Long bodegaId) {
        return ResponseEntity.ok(auditoriaService.buscarPorBodegaOrigen(bodegaId));
    }

    // BUSCAR POR BODEGA DE DESTINO
    @Operation(summary = "Buscar transferencias por bodega de destino")
    @GetMapping("/bodega-destino/{bodegaId}")
    public ResponseEntity<List<AuditoriaTransferenciaDtoResponse>> buscarPorBodegaDestino(@PathVariable @Positive Long bodegaId) {
        return ResponseEntity.ok(auditoriaService.buscarPorBodegaDestino(bodegaId));
    }
}
