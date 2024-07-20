package com.gestion.ReporteActividadGym.Modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "actividades")
public class ReporteActividad {
    @Id
    private String id;
    private String aprendizId;
    private String nombreAprendiz;
    private String entrenadorId;
    private String nombreEntrenamiento;
    private String fechaEntrenamiento;
    private String tipoEntrenamiento;
    private String duracionEntrenamiento;

}
