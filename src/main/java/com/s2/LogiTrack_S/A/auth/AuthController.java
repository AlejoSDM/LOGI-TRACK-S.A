package com.s2.LogiTrack_S.A.auth;

import com.s2.LogiTrack_S.A.config.JwtService;
import com.s2.LogiTrack_S.A.exception.BusinessRuleException;
import com.s2.LogiTrack_S.A.model.Persona;
import com.s2.LogiTrack_S.A.model.Usuario;
import com.s2.LogiTrack_S.A.repository.PersonaRepository;
import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public record RegisterRequest(@NotBlank String nombre,
                                  @NotBlank @Size(min = 6, max = 50) String username,
                                  @NotBlank @Size(min = 8, max = 50) String password) {}

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest r) {
        Usuario u = usuarioRepository.findByUsername(r.username())
                .filter(x -> encoder.matches(r.password(), x.getContraseña()))
                .orElseThrow(() -> new BusinessRuleException("Credenciales inválidas"));
        Map<String, Object> res = new HashMap<>();
        res.put("estado", u.getEstado());
        res.put("rol", u.getRol().name());
        res.put("usuarioId", u.getId());
        res.put("username", u.getUsername());
        res.put("nombre", u.getPersonaId().getNombre());
        // Usuario pendiente de aprobación: NO se entrega token
        if (Boolean.TRUE.equals(u.getEstado()))
            res.put("token", jwtService.generateToken(u.getUsername(), u.getRol().name(), u.getId()));
        return res;
    }

    @PostMapping("/register")
    @Transactional
    public Map<String, String> register(@Valid @RequestBody RegisterRequest r) {
        if (usuarioRepository.existsByUsername(r.username()))
            throw new BusinessRuleException("El nombre de usuario ya existe");
        Persona p = new Persona();
        p.setNombre(r.nombre());
        personaRepository.save(p);
        Usuario u = new Usuario();
        u.setPersonaId(p);
        u.setUsername(r.username());
        u.setContraseña(encoder.encode(r.password()));
        u.setRol(Usuario.Rol.EMPLEADO);
        u.setEstado(false); // pendiente hasta que el ADMIN lo acepte
        usuarioRepository.save(u);
        return Map.of("mensaje", "Solicitud enviada. Espera a que el administrador te acepte.");
    }
}

/** Panel del administrador: solicitudes pendientes. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solicitudes")
class SolicitudController {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    @GetMapping
    public List<Map<String, Object>> pendientes() {
        return usuarioRepository.findByRolAndEstado(Usuario.Rol.EMPLEADO, false).stream()
                .map(u -> Map.<String, Object>of("id", u.getId(), "username", u.getUsername(),
                        "nombre", u.getPersonaId().getNombre())).toList();
    }

    @PutMapping("/{id}/aprobar")
    public Map<String, String> aprobar(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        u.setEstado(true);
        usuarioRepository.save(u);
        return Map.of("mensaje", "Empleado aprobado");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void rechazar(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(u);
        personaRepository.delete(u.getPersonaId());
    }
}

@Component
@RequiredArgsConstructor
class AdminSeed implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.findByRol(Usuario.Rol.ADMIN).isEmpty()) return;
        Persona p = new Persona();
        p.setNombre("Administrador");
        personaRepository.save(p);
        Usuario u = new Usuario();
        u.setPersonaId(p);
        u.setUsername("admin");
        u.setContraseña(new BCryptPasswordEncoder().encode("Admin1234"));
        u.setRol(Usuario.Rol.ADMIN);
        u.setEstado(true);
        usuarioRepository.save(u);
    }
}