package com.gestion.ReporteActividadGym.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "actividadesEntrenamiento")
public class ActividadEntrenamiento {

    @Id
    private String id;
    private String tipoActividad;
    private Date fechaActividad;
    private String aprendizId;
    private String entrenadorId;

    public ActividadEntrenamiento() {
    }

    public ActividadEntrenamiento(String id, String tipoActividad, Date fechaActividad, String aprendizId, String entrenadorId) {
        this.id = id;
        this.tipoActividad = tipoActividad;
        this.fechaActividad = fechaActividad;
        this.aprendizId = aprendizId;
        this.entrenadorId = entrenadorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getAprendizId() {
        return aprendizId;
    }

    public void setAprendizId(String aprendizId) {
        this.aprendizId = aprendizId;
    }

    public String getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(String entrenadorId) {
        this.entrenadorId = entrenadorId;
    }
}
