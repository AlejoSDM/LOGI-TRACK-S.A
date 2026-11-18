
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.CiudadDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CiudadDtoResponse;
import com.s2.LogiTrack_S.A.service.CiudadService;
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
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
@Tag(name = "Ciudades", description = "Gestión de ciudades")
public class CiudadController {

    private final CiudadService ciudadService;

    // GUARDAR CIUDADES

    @Operation(summary = "Registrar una ciudad", description = "Registra una nueva ciudad en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ciudad registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o la ciudad ya existe")
    })
    @PostMapping
    public ResponseEntity<CiudadDtoResponse> guardar(@Valid @RequestBody CiudadDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.guardar(dto));
    }


    // ACTUALIZAR CIUDADES

    @Operation(summary = "Actualizar una ciudad", description = "Actualiza el nombre de una ciudad existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre duplicado"),
            @ApiResponse(responseCode = "404", description = "No existe la ciudad")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CiudadDtoResponse> actualizar(@Parameter(description = "Identificador de la ciudad", example = "1") @PathVariable Long id, @Valid @RequestBody CiudadDtoRequest dto) {
        return ResponseEntity.ok(ciudadService.actualizar(dto, id));
    }


    // ELIMINAR CIUDADES

    @Operation(summary = "Eliminar una ciudad", description = "Elimina una ciudad siempre que no tenga bodegas asociadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ciudad eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La ciudad tiene bodegas asociadas"),
            @ApiResponse(responseCode = "404", description = "No existe la ciudad")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador de la ciudad", example = "1") @PathVariable Long id) {
        ciudadService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // LISTAR CIUDADES

    @Operation(summary = "Listar todas las ciudades", description = "Obtiene el listado completo de ciudades registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de ciudades obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CiudadDtoResponse>> listar() {
        return ResponseEntity.ok(ciudadService.listar());
    }


    // BUSCAR CIUDAD POR ID

    @Operation(summary = "Buscar ciudad por ID", description = "Obtiene los datos de una ciudad mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe la ciudad")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CiudadDtoResponse> buscarId(@Parameter(description = "Identificador de la ciudad", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(ciudadService.buscarId(id));
    }
}
