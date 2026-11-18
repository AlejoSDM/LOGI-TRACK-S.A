
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.ProductosDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.ProductosDtoResponse;
import com.s2.LogiTrack_S.A.service.ProductosService;
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
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductosController {

    private final ProductosService productosService;


    // GUARDAR PRODUCTOS

    @Operation(summary = "Registrar un producto", description = "Registra un producto asociándolo con una marca y una categoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o producto duplicado"),
            @ApiResponse(responseCode = "404", description = "No existe la marca o categoría indicada")
    })
    @PostMapping
    public ResponseEntity<ProductosDtoResponse> guardar(@Valid @RequestBody ProductosDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productosService.guardar(dto));
    }


    // ACTUALIZAR PRODUCTOS

    @Operation(summary = "Actualizar un producto", description = "Actualiza la información de un producto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o producto duplicado"),
            @ApiResponse(responseCode = "404", description = "No existe el producto, marca o categoría")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductosDtoResponse> actualizar(@Parameter(description = "Identificador del producto", example = "1") @PathVariable Long id, @Valid @RequestBody ProductosDtoRequest dto) {
        return ResponseEntity.ok(productosService.actualizar(dto, id));
    }


    // ELIMINAR PRODUCTOS

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto siempre que no tenga inventario ni movimientos asociados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "El producto tiene registros asociados"),
            @ApiResponse(responseCode = "404", description = "No existe el producto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador del producto", example = "1") @PathVariable Long id) {
        productosService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // LISTAR PRODUCTOS

    @Operation(summary = "Listar todos los productos", description = "Obtiene todos los productos registrados.")
    @ApiResponse(responseCode = "200", description = "Listado de productos obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ProductosDtoResponse>> listar() {
        return ResponseEntity.ok(productosService.listar());
    }


    // BUSCAR PRODUCTO POR ID

    @Operation(summary = "Buscar producto por ID", description = "Obtiene un producto mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el producto")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDtoResponse> buscarId(@Parameter(description = "Identificador del producto", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(productosService.buscarId(id));
    }


    // BUSCAR PRODUCTOS POR NOMBRE

    @Operation(summary = "Buscar productos por nombre", description = "Busca productos cuyo nombre contenga el texto indicado.")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductosDtoResponse>> buscarPorNombre(@Parameter(description = "Nombre o parte del nombre del producto", example = "Laptop") @RequestParam String nombre) {
        return ResponseEntity.ok(productosService.buscarPorNombre(nombre));
    }


    // BUSCAR PRODUCTOS POR CATEGORÍA

    @Operation(summary = "Buscar productos por categoría", description = "Obtiene los productos asociados a una categoría.")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductosDtoResponse>> buscarPorCategoria(@Parameter(description = "Identificador de la categoría", example = "1") @PathVariable Long categoriaId) {
        return ResponseEntity.ok(productosService.buscarPorCategoria(categoriaId));
    }


    // BUSCAR PRODUCTOS POR MARCA

    @Operation(summary = "Buscar productos por marca", description = "Obtiene los productos asociados a una marca.")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ProductosDtoResponse>> buscarPorMarca(@Parameter(description = "Identificador de la marca", example = "1") @PathVariable Long marcaId) {
        return ResponseEntity.ok(productosService.buscarPorMarca(marcaId));
    }
}
