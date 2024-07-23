package com.gestion.ReporteActividadGym.Repositorio;

import com.gestion.ReporteActividadGym.Modelo.Actividad;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActividadRepositorio extends MongoRepository<Actividad, String> {
    List<Actividad> findByAprendizId(Long aprendizId);

    List<Actividad> findByAprendizIdAndFechaEntrenamientoAndNombreEntrenamientoAndTipoEntrenamientoAndDuracionEntrenamiento(
            Long aprendizId,
            LocalDate fechaEntrenamiento,
            String nombreEntrenamiento,
            String tipoEntrenamiento,
            String duracionEntrenamiento
    );
}
