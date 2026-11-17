package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_auditorias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReportesAuditorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "transferencia")
    private AuditoriaTransferencia transferenciaId;

    @ManyToOne
    @JoinColumn(name = "salida_entrada")
    private AuditoriaSalidaEntrada salidaEntradaId;
}