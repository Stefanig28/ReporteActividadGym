package com.gestion.ReporteActividadGym.Repositorio;

import com.gestion.ReporteActividadGym.Modelo.ReporteActividad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteActividadRepositorio extends MongoRepository<ReporteActividad, String> {
    List<ReporteActividad> findByAprendizId(String aprendizId);
}
