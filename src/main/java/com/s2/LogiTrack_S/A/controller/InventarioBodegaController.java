
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.InventarioBodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.InventarioBodegaDtoResponse;
import com.s2.LogiTrack_S.A.service.InventarioBodegaService;
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
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
@Validated
@Tag(name = "Inventario de bodegas", description = "Gestión y consulta del inventario almacenado en las bodegas")
public class InventarioBodegaController {

    private final InventarioBodegaService inventarioBodegaService;

    // GUARDAR INVENTARIO
    @Operation(summary = "Registrar inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o inventario duplicado"),
            @ApiResponse(responseCode = "404", description = "No existe la bodega o el producto")
    })
    @PostMapping
    public ResponseEntity<InventarioBodegaDtoResponse> guardar(@Valid @RequestBody InventarioBodegaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioBodegaService.guardar(dto));
    }

    // ACTUALIZAR INVENTARIO
    @Operation(summary = "Actualizar inventario")
    @PutMapping("/{id}")
    public ResponseEntity<InventarioBodegaDtoResponse> actualizar(@Parameter(description = "ID del inventario") @PathVariable Long id, @Valid @RequestBody InventarioBodegaDtoRequest dto) {
        return ResponseEntity.ok(inventarioBodegaService.actualizar(dto, id));
    }

    // ELIMINAR INVENTARIO
    @Operation(summary = "Eliminar inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inventario eliminado"),
            @ApiResponse(responseCode = "400", description = "El inventario todavía tiene unidades"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioBodegaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // LISTAR Y BUSCAR INVENTARIO
    @Operation(summary = "Listar inventarios")
    @GetMapping
    public ResponseEntity<List<InventarioBodegaDtoResponse>> listar() {
        return ResponseEntity.ok(inventarioBodegaService.listar());
    }

    @Operation(summary = "Buscar inventario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioBodegaDtoResponse> buscarId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioBodegaService.buscarId(id));
    }

    // BUSCAR INVENTARIO POR BODEGA, PRODUCTO Y STOCK BAJO
    @Operation(summary = "Buscar inventario por bodega")
    @GetMapping("/bodega/{bodegaId}")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarBodega(@PathVariable Long bodegaId) {
        return ResponseEntity.ok(inventarioBodegaService.buscarBodega(bodegaId));
    }

    @Operation(summary = "Buscar inventario por producto")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(inventarioBodegaService.buscarProducto(productoId));
    }

    @Operation(summary = "Buscar inventarios con stock bajo")
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<InventarioBodegaDtoResponse>> buscarStockBajo(@RequestParam @Positive Integer limite) {
        return ResponseEntity.ok(inventarioBodegaService.buscarStockBajo(limite));
    }

    // SUMAR STOCK POR BODEGA
    @Operation(summary = "Consultar el stock total de una bodega")
    @GetMapping("/bodega/{bodegaId}/total-stock")
    public ResponseEntity<Long> sumarStockPorBodega(@PathVariable Long bodegaId) {
        return ResponseEntity.ok(inventarioBodegaService.sumarStockPorBodega(bodegaId));
    }
}
