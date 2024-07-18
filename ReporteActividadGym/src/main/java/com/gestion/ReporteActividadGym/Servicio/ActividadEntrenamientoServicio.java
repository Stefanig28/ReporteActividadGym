package com.gestion.ReporteActividadGym.Servicio;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import com.gestion.ReporteActividadGym.Repositorio.ActividadEntrenamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActividadEntrenamientoServicio {

    private final ActividadEntrenamientoRepositorio actividadEntrenamientoRepositorio;
    private final RestTemplate restTemplate;

    @Autowired
    public ActividadEntrenamientoServicio(ActividadEntrenamientoRepositorio actividadEntrenamientoRepositorio, RestTemplate restTemplate) {
        this.actividadEntrenamientoRepositorio = actividadEntrenamientoRepositorio;
        this.restTemplate = restTemplate;
    }

    public ActividadEntrenamiento guardarActividad(Long aprendizId, Long entrenadorId, String tipoEntrenamiento, int duracion) {
        ActividadEntrenamiento actividad = new ActividadEntrenamiento();
        actividad.setAprendizId(aprendizId);
        actividad.setEntrenadorId(entrenadorId);
        actividad.setTipoEntrenamiento(tipoEntrenamiento);
        actividad.setDuracionEntrenamiento(duracion);
        actividad.setFechaEntrenamiento(LocalDate.now());

        actividad.setNombreAprendiz(fetchAprendizName(aprendizId));
        actividad.setNombreEntrenador(fetchEntrenadorName(entrenadorId));

        return actividadEntrenamientoRepositorio.save(actividad);
    }

    public List<ActividadEntrenamiento> obtenerActividades() {
        return actividadEntrenamientoRepositorio.findAll();
    }

    public List<ActividadEntrenamiento> obtenerActividadesPorAprendiz(Long aprendizId) {
        return actividadEntrenamientoRepositorio.findByAprendizId(aprendizId);
    }

    public void eliminarActividad(String id) {
        actividadEntrenamientoRepositorio.deleteById(id);
    }

    private String fetchAprendizName(Long aprendizId) {
        String url = "http://localhost:8081/api/aprendiz/" + aprendizId;
        return restTemplate.getForObject(url, String.class);
    }

    private String fetchEntrenadorName(Long entrenadorId) {
        String url = "http://localhost:8081/api/entrenador/" + entrenadorId;
        return restTemplate.getForObject(url, String.class);
    }
}




