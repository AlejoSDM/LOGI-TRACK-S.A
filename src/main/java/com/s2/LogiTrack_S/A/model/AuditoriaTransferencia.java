package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_transferencia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuditoriaTransferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_responsable", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "bodega_origen", nullable = false)
    private Bodega bodegaOrigenId;

    @ManyToOne
    @JoinColumn(name = "bodega_destino", nullable = false)
    private Bodega bodegaDestinoId;

    @ManyToOne
    @JoinColumn(name = "producto_fk", nullable = false)
    private Productos productoId;

    @Column(nullable = false)
    private Integer cantidad;
}