package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario_bodega")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventarioBodega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bodega_fk", nullable = false)
    private Bodega bodegaId;

    @ManyToOne
    @JoinColumn(name = "producto_fk", nullable = false)
    private Productos productoId;

    @Column(nullable = false)
    private Integer stock;
}