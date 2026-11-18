
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.BodegaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.BodegaDtoResponse;
import com.s2.LogiTrack_S.A.service.BodegaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bodegas")
@RequiredArgsConstructor
@Tag(name = "Bodegas", description = "Gestión de bodegas, ubicación y empleados encargados"
)
public class BodegaController {

    private final BodegaService bodegaService;

    // GUARDAR BODEGAS
    @Operation(summary = "Registrar una bodega", description = "Registra una nueva bodega asociándola con una ciudad y un empleado encargado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bodega registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incumplimiento de reglas de negocio"),
            @ApiResponse(responseCode = "404", description = "No existe la ciudad o el empleado indicado")
    })
    @PostMapping
    public ResponseEntity<BodegaDtoResponse> guardar(@Valid @RequestBody BodegaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bodegaService.guardar(dto));
    }


    // ACTUALIZAR BODEGAS

    @Operation(summary = "Actualizar una bodega", description = "Actualiza la información de una bodega existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bodega actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incumplimiento de reglas de negocio"),
            @ApiResponse(responseCode = "404", description = "No existe la bodega, ciudad o empleado indicado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BodegaDtoResponse> actualizar(@Parameter(description = "Identificador de la bodega", example = "1") @PathVariable Long id, @Valid @RequestBody BodegaDtoRequest dto) {
        return ResponseEntity.ok(bodegaService.actualizar(dto, id));
    }


    // ELIMINAR BODEGAS

    @Operation(summary = "Eliminar una bodega", description = "Elimina una bodega siempre que no tenga inventario ni movimientos asociados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bodega eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La bodega tiene inventario o movimientos asociados"),
            @ApiResponse(responseCode = "404", description = "No existe la bodega")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador de la bodega", example = "1") @PathVariable Long id) {
        bodegaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // LISTAR BODEGAS

    @Operation(summary = "Listar todas las bodegas", description = "Obtiene el listado completo de bodegas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de bodegas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<BodegaDtoResponse>>listar()
        {   return ResponseEntity.ok(bodegaService.listar());
    }


    // BUSCAR BODEGAS POR ID

    @Operation(summary = "Buscar bodega por ID", description = "Obtiene los datos de una bodega mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bodega encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe la bodega")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BodegaDtoResponse>buscarId(@Parameter(description = "Identificador de la bodega", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(bodegaService.buscarId(id));
    }


    // BUSCAR BODEGAS POR NOMBRE

    @Operation(summary = "Buscar bodegas por nombre", description = "Busca bodegas cuyo nombre coincida parcial o completamente con el texto proporcionado.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    @GetMapping("/buscar")
    public ResponseEntity<List<BodegaDtoResponse>>buscarNombre(@Parameter(description = "Nombre o parte del nombre de la bodega", example = "Central") @RequestParam String nombre) {
        return ResponseEntity.ok(bodegaService.buscarNombre(nombre));
    }


    // BUSCAR BODEGAS POR CIUDAD

    @Operation(summary = "Buscar bodegas por ciudad", description = "Obtiene todas las bodegas asociadas a una ciudad.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    @GetMapping("/ciudad/{ciudadId}")
    public ResponseEntity<List<BodegaDtoResponse>>buscarCiudad(@Parameter(description = "Identificador de la ciudad", example = "1") @PathVariable Long ciudadId) {
        return ResponseEntity.ok(bodegaService.buscarCiudad(ciudadId));
    }


    // BUSCAR BODEGAS POR EMPLEADO

    @Operation(summary = "Buscar bodegas por empleado", description = "Obtiene las bodegas asignadas a un empleado encargado.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    @GetMapping("/empleado/{usuarioId}")
    public ResponseEntity<List<BodegaDtoResponse>>buscarEmpleado(@Parameter(description = "Identificador del usuario encargado", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(bodegaService.buscarEmpleado(usuarioId));
    }
}
