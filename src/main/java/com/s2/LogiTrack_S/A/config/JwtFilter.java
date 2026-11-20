package com.s2.LogiTrack_S.A.config;

import com.s2.LogiTrack_S.A.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //OBTIENE EL TOKEN DE LA SOLICITUD
        String header = ((HttpServletRequest) request).getHeader("Authorization");
        //COMPRUEBA QUE ESE TOKEN EXISTA Y QUE COMIENCE CON EL BEARER
        if (header != null && header.startsWith("Bearer ")) {
            //EL HEADER.SUBSTRING ELIMINA LOS PRIMEROS 7 CARACTERES EN ESTE CASO LA PALABRA
            //BEARER PARA PODER QUEDARSE SOLO CON EL TOKEN
            //SI EL VALIDATION ESTA BIEN RECIBE LOS CLAIMS QUE SON LOS DATOS DEL USUARIO
            Claims c = jwtService.validateToken(header.substring(7));
            if (c != null) {
                usuarioRepository.findByUsername(c.getSubject())
                        .filter(u -> Boolean.TRUE.equals(u.getEstado()))
                        .ifPresent(u -> {
                            var auth = new UsernamePasswordAuthenticationToken(
                                    u.getUsername(), null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + u.getRol().name())));
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        });
            }
        }
        chain.doFilter(request, response);
    }
}

