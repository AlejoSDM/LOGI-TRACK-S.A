package com.s2.LogiTrack_S.A.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "marca_fk", nullable = false)
    private Marca marcaId;

    @ManyToOne
    @JoinColumn(name = "categoria_fk", nullable = false)
    private CategoriaProducto categoriaId;

    @Column(name = "precio_u", nullable = false)
    private BigDecimal precioUnitario;
}