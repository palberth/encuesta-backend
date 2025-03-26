package com.encuesta.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "encuestas")
public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private String paisResidencia;
    private String nacionalidad;
    private String sexo;
    private Integer edad;
    private String viajaCon;
    private Integer cantidadPersonas;
    private String motivoViaje;
    private String organizacionViaje;

    @JdbcTypeCode(SqlTypes.JSON)
    private String serviciosPaquete;

    @JdbcTypeCode(SqlTypes.JSON)
    private String gastosPaquete;

    @JdbcTypeCode(SqlTypes.JSON)
    private String gastosTransporte;

    @JdbcTypeCode(SqlTypes.JSON)
    private String paisesVisita;
}
