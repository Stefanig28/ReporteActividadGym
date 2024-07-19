package com.gestion.ReporteActividadGym.Repositorio;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadEntrenamientoRepositorio extends MongoRepository<ActividadEntrenamiento, String> {
    List<ActividadEntrenamiento> findByAprendizId(String aprendizId);
}

