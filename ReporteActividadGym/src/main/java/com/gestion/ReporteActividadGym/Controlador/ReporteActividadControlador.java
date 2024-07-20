package com.gestion.ReporteActividadGym.Controlador;

import com.gestion.ReporteActividadGym.Modelo.ReporteActividad;
import com.gestion.ReporteActividadGym.Servicio.ReporteActividadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades")
public class ReporteActividadControlador {

    private final ReporteActividadServicio reporteActividadServicio;

    @Autowired
    public ReporteActividadControlador(ReporteActividadServicio reporteActividadServicio) {
        this.reporteActividadServicio = reporteActividadServicio;
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardadActividad(@RequestBody ReporteActividad actividad) {
        reporteActividadServicio.guardarActividad(actividad);
        return ResponseEntity.ok("Se guardó la actividad correctamente");
    }

    @GetMapping
    public ResponseEntity<List<ReporteActividad>> obtenerActividades() {
        List<ReporteActividad> actividades = reporteActividadServicio.obtenerActividades();
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/aprendiz/{aprendizId}")
    public ResponseEntity<List<ReporteActividad>> obtenerActividadesPorAprendiz(@PathVariable String aprendizId) {
        List<ReporteActividad> actividades = reporteActividadServicio.obtenerActividadesPorAprendiz(aprendizId);
        return ResponseEntity.ok(actividades);
    }
}
