package com.gestion.ReporteActividadGym.Modelo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Setter
@Getter
@Document(collection = "actividades")
public class Actividad {
    @Id
    private String id;
    private Long aprendizId;
    private Long entrenadorId;
    private String nombreEntrenamiento;
    private LocalDate fechaEntrenamiento;
    private String tipoEntrenamiento;
    private String duracionEntrenamiento;

}
