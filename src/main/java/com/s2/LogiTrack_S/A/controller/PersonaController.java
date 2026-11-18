
package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.PersonaDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.PersonaDtoResponse;
import com.s2.LogiTrack_S.A.service.PersonaService;
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
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Gestión de personas")
public class PersonaController {

    private final PersonaService personaService;

    // GUARDAR PERSONAS

    @Operation(summary = "Registrar una persona", description = "Registra una nueva persona en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PersonaDtoResponse> guardar(@Valid @RequestBody PersonaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.guardar(dto));
    }


    // ACTUALIZAR PERSONAS

    @Operation(summary = "Actualizar una persona", description = "Actualiza la información de una persona existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "No existe la persona")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDtoResponse> actualizar(@Parameter(description = "Identificador de la persona", example = "1") @PathVariable Long id, @Valid @RequestBody PersonaDtoRequest dto) {
        return ResponseEntity.ok(personaService.actualizar(dto, id));
    }


    // ELIMINAR PERSONAS

    @Operation(summary = "Eliminar una persona", description = "Elimina una persona siempre que no tenga un usuario asociado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La persona tiene un usuario asociado"),
            @ApiResponse(responseCode = "404", description = "No existe la persona")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "Identificador de la persona", example = "1") @PathVariable Long id) {
        personaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // LISTAR PERSONAS

    @Operation(summary = "Listar todas las personas", description = "Obtiene el listado completo de personas registradas.")
    @ApiResponse(responseCode = "200", description = "Listado de personas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PersonaDtoResponse>> listar() {
        return ResponseEntity.ok(personaService.listar());
    }


    // BUSCAR PERSONA POR ID

    @Operation(summary = "Buscar persona por ID", description = "Obtiene los datos de una persona mediante su identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe la persona")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDtoResponse> buscarId(@Parameter(description = "Identificador de la persona", example = "1")@PathVariable Long id) {
        return ResponseEntity.ok(personaService.buscarId(id));
    }


    // BUSCAR PERSONAS POR NOMBRE

    @Operation(summary = "Buscar personas por nombre", description = "Busca personas cuyo nombre contenga el texto indicado, sin distinguir mayúsculas y minúsculas.")
    @ApiResponse(responseCode = "200", description = "Resultados de la búsqueda")
    @GetMapping("/buscar")
    public ResponseEntity<List<PersonaDtoResponse>> buscarPorNombre(@Parameter(description = "Nombre o parte del nombre que se desea buscar", example = "Alejandro") @RequestParam String nombre) {
        return ResponseEntity.ok(personaService.buscarPorNombre(nombre));
    }
}
