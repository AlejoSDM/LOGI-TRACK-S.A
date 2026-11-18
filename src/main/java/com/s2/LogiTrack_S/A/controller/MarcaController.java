
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.MarcaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.MarcaDtoResponse;
import com.s2.LogiTrack_S.A.service.MarcaService;
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
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Gestión de marcas de productos")
public class MarcaController {

    private final MarcaService marcaService;

    // GUARDAR MARCAS

    @Operation(summary = "Registrar una marca", description = "Registra una nueva marca en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o marca duplicada")
    })
    @PostMapping
    public ResponseEntity<MarcaDtoResponse> guardar(@Valid @RequestBody MarcaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.guardar(dto));
    }


    // ACTUALIZAR MARCAS

    @Operation(summary = "Actualizar una marca", description = "Actualiza la información de una marca existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre duplicado"),
            @ApiResponse(responseCode = "404", description = "No existe la marca")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MarcaDtoResponse> actualizar(@Parameter(description = "Identificador de la marca", example = "1") @PathVariable Long id, @Valid @RequestBody MarcaDtoRequest dto) {
        return ResponseEntity.ok(marcaService.actualizar(dto, id));
    }


    // ELIMINAR MARCAS

    @Operation(summary = "Eliminar una marca", description = "Elimina una marca siempre que no tenga productos asociados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marca eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La marca tiene productos asociados"),
            @ApiResponse(responseCode = "404", description = "No existe la marca")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador de la marca", example = "1") @PathVariable Long id) {
        marcaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // LISTAR MARCAS

    @Operation(summary = "Listar todas las marcas", description = "Obtiene todas las marcas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de marcas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MarcaDtoResponse>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }


    // BUSCAR MARCA POR ID

    @Operation(summary = "Buscar marca por ID", description = "Obtiene una marca mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe la marca")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDtoResponse> buscarId(@Parameter(description = "Identificador de la marca", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarId(id));
    }


    // BUSCAR MARCAS POR NOMBRE

    @Operation(summary = "Buscar marcas por nombre", description = "Busca marcas cuyo nombre contenga el texto indicado, sin distinguir mayúsculas y minúsculas.")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    @GetMapping("/buscar")
    public ResponseEntity<List<MarcaDtoResponse>> buscarPorNombre(@Parameter(description = "Nombre o parte del nombre de la marca", example = "Sam") @RequestParam String nombre) {
        return ResponseEntity.ok(marcaService.buscarPorNombre(nombre));
    }
}
