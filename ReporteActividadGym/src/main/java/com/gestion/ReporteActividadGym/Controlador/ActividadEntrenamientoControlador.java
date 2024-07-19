package com.gestion.ReporteActividadGym.Controlador;

import com.gestion.ReporteActividadGym.Modelo.ActividadEntrenamiento;
import com.gestion.ReporteActividadGym.Servicio.ActividadEntrenamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<String> crearActividad(@RequestBody ActividadEntrenamiento actividad) {
        actividadEntrenamientoServicio.crearActividad(actividad);
        return ResponseEntity.ok("Se creó la actividad correctamente");
    }

    @GetMapping("/{idAprendiz}")
    public ResponseEntity<List<ActividadEntrenamiento>> obtenerActividadesPorAprendiz(@PathVariable String idAprendiz) {
        List<ActividadEntrenamiento> actividades = actividadEntrenamientoServicio.obtenerActividadesPorAprendiz(idAprendiz);
        return ResponseEntity.ok(actividades);
    }
}
