package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_salida_entrada")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuditoriaSalidaEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_accion", nullable = false)
    private TipoAccion tipoAccion;

    @ManyToOne
    @JoinColumn(name = "usuario_responsable", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "bodega_fk", nullable = false)
    private Bodega bodegaId;

    @ManyToOne
    @JoinColumn(name = "producto_fk", nullable = false)
    private Productos productoId;

    @Column(nullable = false)
    private Integer cantidad;

    public enum TipoAccion {
        ENTRADA, SALIDA
    }
}