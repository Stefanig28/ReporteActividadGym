package com.gestion.ReporteActividadGym.Controlador;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import com.gestion.ReporteActividadGym.Servicio.ActividadEntrenamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades")
public class ActividadEntrenamientoControlador {

    private final ActividadEntrenamientoServicio actividadEntrenamientoServicio;

    @Autowired
    public ActividadEntrenamientoControlador(ActividadEntrenamientoServicio actividadEntrenamientoServicio) {
        this.actividadEntrenamientoServicio = actividadEntrenamientoServicio;
    }

    @PostMapping("/guardar")
    public ResponseEntity<ActividadEntrenamiento> guardarActividad(@RequestBody ActividadEntrenamiento actividad) {
        ActividadEntrenamiento nuevaActividad = actividadEntrenamientoServicio.guardarActividad(actividad.getAprendizId(), actividad.getEntrenadorId(), actividad.getTipoEntrenamiento(), actividad.getDuracionEntrenamiento());
        return ResponseEntity.ok(nuevaActividad);
    }

    @GetMapping
    public List<ActividadEntrenamiento> obtenerActividades() {
        return actividadEntrenamientoServicio.obtenerActividades();
    }

    @GetMapping("/aprendiz/{aprendizId}")
    public ResponseEntity<List<ActividadEntrenamiento>> obtenerActividadesPorAprendiz(@PathVariable Long aprendizId) {
        return ResponseEntity.ok(actividadEntrenamientoServicio.obtenerActividadesPorAprendiz(aprendizId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable String id) {
        actividadEntrenamientoServicio.eliminarActividad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
