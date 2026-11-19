
package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_auditorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportesAuditorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @Column(nullable = false, length = 50)
    private String modulo;

    @Column(name = "tipo_accion", nullable = false, length = 30)
    private String tipoAccion;

    @Column(name = "registro_id")
    private Long registroId;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @PrePersist
    public void asignarFecha() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
