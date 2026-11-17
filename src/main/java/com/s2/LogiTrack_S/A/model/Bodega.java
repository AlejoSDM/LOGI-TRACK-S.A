package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bodega")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Bodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ciudad_fk", nullable = false)
    private Ciudad ciudadId;

    @Column(nullable = false)
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "empleado_fk", nullable = false)
    private Usuario usuarioId;
}