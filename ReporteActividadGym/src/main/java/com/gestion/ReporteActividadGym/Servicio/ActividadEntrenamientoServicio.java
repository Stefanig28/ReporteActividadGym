package com.gestion.ReporteActividadGym.Servicio;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import com.gestion.ReporteActividadGym.Repositorio.ActividadEntrenamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadEntrenamientoServicio {
    private final ActividadEntrenamientoRepositorio actividadEntrenamientoRepositorio;

    @Autowired
    public ActividadEntrenamientoServicio(ActividadEntrenamientoRepositorio actividadEntrenamientoRepositorio) {
        this.actividadEntrenamientoRepositorio = actividadEntrenamientoRepositorio;
    }

    public void crearActividad(ActividadEntrenamiento actividad) {
        actividadEntrenamientoRepositorio.save(actividad);
    }

    public List<ActividadEntrenamiento> obtenerActividadesPorAprendiz(String aprendizId) {
        return actividadEntrenamientoRepositorio.findByAprendizId(aprendizId);
    }

}
