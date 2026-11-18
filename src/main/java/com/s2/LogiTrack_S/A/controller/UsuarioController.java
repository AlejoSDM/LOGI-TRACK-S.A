package com.s2.LogiTrack_S.A.controller;

import com.s2.LogiTrack_S.A.dto.request.UsuarioDtoRequest;
import com.s2.LogiTrack_S.A.dto.response.UsuarioDtoResponse;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GUARDAR USUARIO

    @PostMapping
    @Operation(summary = "Registrar usuario")
    public ResponseEntity<UsuarioDtoResponse> guardar(@RequestBody UsuarioDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(dto));
    }

    // ACTUALIZAR USUARIO

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<UsuarioDtoResponse> actualizar(@RequestBody UsuarioDtoRequest dto, @Parameter(description = "ID del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.actualizar(dto, id));
    }

    // ELIMINAR USUARIO

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // LISTAR USUARIOS Y BUSCAR POR ID, ROL Y ESTADO

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public ResponseEntity<List<UsuarioDtoResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID")
    public ResponseEntity<UsuarioDtoResponse> buscarId(@Parameter(description = "ID del usuario") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarId(id));
    }

    @GetMapping("/rol/{rol}")
    @Operation(summary = "Buscar usuarios por rol")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarPorRol(@Parameter(description = "Rol del usuario: ADMIN o EMPLEADO") @PathVariable Usuario.Rol rol) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(rol));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar usuarios por estado")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarPorEstado(@Parameter(description = "Estado del usuario: true o false") @PathVariable Boolean estado) {
        return ResponseEntity.ok(usuarioService.buscarPorEstado(estado));
    }

    // BUSCAR POR ROL Y ESTADO

    @GetMapping("/rol/{rol}/estado/{estado}")
    @Operation(summary = "Buscar usuarios por rol y estado")
    public ResponseEntity<List<UsuarioDtoResponse>> buscarPorRolYEstado(@Parameter(description = "Rol del usuario: ADMIN o EMPLEADO") @PathVariable Usuario.Rol rol, @Parameter(description = "Estado del usuario: true o false") @PathVariable Boolean estado) {
        return ResponseEntity.ok(usuarioService.buscarPorRolYEstado(rol, estado));
    }
}