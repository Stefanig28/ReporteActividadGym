package com.gestion.ReporteActividadGym.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "actividades")
public class ActividadEntrenamiento {
    @Id
    private String id;
    private Long aprendizId;
    private String nombreAprendiz;
    private Long entrenadorId;
    private String nombreEntrenador;
    private String nombreEntrenamiento;
    private LocalDate fechaEntrenamiento;
    private String tipoEntrenamiento;
    private int duracionEntrenamiento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAprendizId() {
        return aprendizId;
    }

    public void setAprendizId(Long aprendizId) {
        this.aprendizId = aprendizId;
    }

    public String getNombreAprendiz() {
        return nombreAprendiz;
    }

    public void setNombreAprendiz(String nombreAprendiz) {
        this.nombreAprendiz = nombreAprendiz;
    }

    public Long getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(Long entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }

    public String getNombreEntrenamiento() {
        return nombreEntrenamiento;
    }

    public void setNombreEntrenamiento(String nombreEntrenamiento) {
        this.nombreEntrenamiento = nombreEntrenamiento;
    }

    public LocalDate getFechaEntrenamiento() {
        return fechaEntrenamiento;
    }

    public void setFechaEntrenamiento(LocalDate fechaEntrenamiento) {
        this.fechaEntrenamiento = fechaEntrenamiento;
    }

    public String getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public void setTipoEntrenamiento(String tipoEntrenamiento) {
        this.tipoEntrenamiento = tipoEntrenamiento;
    }

    public int getDuracionEntrenamiento() {
        return duracionEntrenamiento;
    }

    public void setDuracionEntrenamiento(int duracionEntrenamiento) {
        this.duracionEntrenamiento = duracionEntrenamiento;
    }
}