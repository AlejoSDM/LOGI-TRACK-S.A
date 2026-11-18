
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.CategoriaProductoDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.CategoriaProductoDtoResponse;
import com.s2.LogiTrack_S.A.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Validated
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    // GUARDAR CATEGORÍA
    @Operation(summary = "Registrar una categoría", description = "Registra una nueva categoría de productos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría duplicada")
    })
    @PostMapping
    public ResponseEntity<CategoriaProductoDtoResponse> guardar(@Valid @RequestBody CategoriaProductoDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaProductoService.guardar(dto));
    }

    // ACTUALIZAR CATEGORÍA
    @Operation(summary = "Actualizar una categoría", description = "Actualiza el tipo de una categoría existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o categoría duplicada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProductoDtoResponse> actualizar(@Parameter(description = "Identificador de la categoría", example = "1") @PathVariable @Positive Long id, @Valid @RequestBody CategoriaProductoDtoRequest dto) {
        return ResponseEntity.ok(categoriaProductoService.actualizar(dto, id));
    }

    // ELIMINAR CATEGORÍA
    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría siempre que no tenga productos asociados."
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"), @ApiResponse(responseCode = "400", description = "La categoría tiene productos asociados"), @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador de la categoría", example = "1") @PathVariable @Positive Long id) {
        categoriaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // LISTAR CATEGORÍAS
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CategoriaProductoDtoResponse>> listar() {
        return ResponseEntity.ok(categoriaProductoService.listar());
    }

    // BUSCAR CATEGORÍA POR ID
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProductoDtoResponse> buscarId(@Parameter(description = "Identificador de la categoría", example = "1") @PathVariable @Positive Long id) {
        return ResponseEntity.ok(categoriaProductoService.buscarId(id));
    }
}
