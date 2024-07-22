package com.gestion.ReporteActividadGym.Controlador;

import com.gestion.ReporteActividadGym.Excepcion.ActividadExistenteExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.ActividadNoEncontradaExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.ActividadNoExistenteExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.AprendizNoEncontradoExcepcion;
import com.gestion.ReporteActividadGym.Modelo.Actividad;
import com.gestion.ReporteActividadGym.Servicio.ActividadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
public class ActividadControlador {

    private final ActividadServicio actividadServicio;

    @Autowired
    public ActividadControlador(ActividadServicio actividadServicio) {
        this.actividadServicio = actividadServicio;
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardadActividad(@RequestBody Actividad actividad) {
        try {
            if (actividad.getAprendizId() == null || actividad.getEntrenadorId() == null) {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("La id del aprendiz y del entrenador son obligatorias.");
            }
            actividadServicio.guardarActividad(actividad);
            return ResponseEntity.ok("Se guardó la actividad correctamente.");
        } catch (ActividadExistenteExcepcion e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado :(");
        }
    }

    @GetMapping
    public ResponseEntity<List<Actividad>> obtenerActividades() {
        try {
            List<Actividad> actividades = actividadServicio.obtenerActividades();
            return ResponseEntity.ok(actividades);
        } catch (ActividadNoExistenteExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/aprendiz/{aprendizId}")
    public ResponseEntity<List<Actividad>> obtenerActividadesPorAprendiz(@PathVariable Long aprendizId) {
        try {
            List<Actividad> actividades = actividadServicio.obtenerActividadesPorAprendiz(aprendizId);
            return ResponseEntity.ok(actividades);
        } catch (AprendizNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<String> actualizarActividad(@PathVariable String id, @RequestBody Actividad actividad) {
        try {
            if (actividad.getAprendizId() == null || actividad.getEntrenadorId() == null) {
                ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("El id del aprendiz y del entrenador son obligatorias.");
            }
            actividadServicio.actualizarActividad(id, actividad);
            return ResponseEntity.ok("Se actualizó la actividad  correctamente.");
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado :(");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarActividad(@PathVariable String id) {
        try {
            actividadServicio.eliminarActividad(id);
            return ResponseEntity.ok("Se eliminó la actividad correctamente.");
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //http://localhost:8081/api/actividades/reporte?aprendizId=1&mes=4&anio=2024
    @GetMapping("/reporte")
    public ResponseEntity<String> obtenerReporteMensual(@RequestParam Long aprendizId, @RequestParam int mes, @RequestParam int anio) {
        try {
            String reporte = actividadServicio.generarReporteMensual(aprendizId, mes, anio);
            return ResponseEntity.ok(reporte);
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
