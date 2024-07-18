package com.gestion.ReporteActividadGym.Repositorio;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActividadEntrenamientoRepositorio extends MongoRepository<ActividadEntrenamiento, String> {
    List<ActividadEntrenamiento> findByAprendizId(Long aprendiZId);
}
