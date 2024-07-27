package com.gestion.ReporteActividadGym.Controlador;

import com.gestion.ReporteActividadGym.Excepcion.*;
import com.gestion.ReporteActividadGym.Modelo.Actividad;
import com.gestion.ReporteActividadGym.Servicio.ActividadServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actividades")
public class ActividadControlador {

    private final ActividadServicio actividadServicio;

    @Autowired
    public ActividadControlador(ActividadServicio actividadServicio) {
        this.actividadServicio = actividadServicio;
    }

    @Operation(summary = "Crear una nueva actividad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad guardada correctamente"),
            @ApiResponse(responseCode = "400", description = "La actividad ya existe o la información está incompleta"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @PostMapping("/crear")
    public ResponseEntity<String> crearActividad(@RequestBody Map<String, Object> requestBody) {
        try {
            Long aprendizId = Long.parseLong(requestBody.get("aprendizId").toString());
            Long entrenadorId = Long.parseLong(requestBody.get("entrenadorId").toString());
            String nombreEntrenamiento = requestBody.get("nombreEntrenamiento").toString();
            LocalDate fechaEntrenamiento = LocalDate.parse(requestBody.get("fechaEntrenamiento").toString());
            String tipoEntrenamiento = requestBody.get("tipoEntrenamiento").toString();
            String duracionEntrenamiento = requestBody.get("duracionEntrenamiento").toString();

            Actividad actividad = new Actividad();
            actividad.setAprendizId(aprendizId);
            actividad.setEntrenadorId(entrenadorId);
            actividad.setNombreEntrenamiento(nombreEntrenamiento);
            actividad.setFechaEntrenamiento(fechaEntrenamiento);
            actividad.setTipoEntrenamiento(tipoEntrenamiento);
            actividad.setDuracionEntrenamiento(String.valueOf(duracionEntrenamiento));

            actividadServicio.crearActividad(actividad);
            return ResponseEntity.status(HttpStatus.CREATED).body("Actividad creada correctamente");
        } catch (ActividadExistenteExcepcion e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La actividad ya existe: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la actividad: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener todas las actividades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de actividades obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron actividades"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @GetMapping
    public ResponseEntity<List<Actividad>> obtenerActividades() {
        try {
            List<Actividad> actividades = actividadServicio.obtenerActividades();
            return ResponseEntity.ok(actividades);
        } catch (ActividadNoExistenteExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Obtener actividades por aprendiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de actividades del aprendiz obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el aprendiz"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @GetMapping("/aprendiz/{aprendizId}")
    public ResponseEntity<List<Actividad>> obtenerActividadesPorAprendiz(
            @Parameter(description = "ID del aprendiz", required = true) @PathVariable Long aprendizId) {
        try {
            List<Actividad> actividades = actividadServicio.obtenerActividadesPorAprendiz(aprendizId);
            return ResponseEntity.ok(actividades);
        } catch (AprendizNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Actualizar una actividad existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la actividad"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<String> actualizarActividad(
            @Parameter(description = "ID de la actividad a actualizar", required = true) @PathVariable String id,
            @Parameter(description = "Datos de la actividad actualizados", required = true) @RequestBody Actividad actividad) {
        try {
            if (actividad.getAprendizId() == null || actividad.getEntrenadorId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id del aprendiz y del entrenador son obligatorias.");
            }
            actividadServicio.actualizarActividad(id, actividad);
            return ResponseEntity.ok("Se actualizó la actividad correctamente.");
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado :(");
        }
    }

    @Operation(summary = "Eliminar una actividad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la actividad"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarActividad(
            @Parameter(description = "ID de la actividad a eliminar", required = true) @PathVariable String id) {
        try {
            actividadServicio.eliminarActividad(id);
            return ResponseEntity.ok("Se eliminó la actividad correctamente.");
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //http://localhost:8081/api/actividades/reporte?aprendizId=1&mes=4&anio=2024
    @Operation(summary = "Obtener reporte mensual de actividades por aprendiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró la actividad"),
            @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado")
    })
    @GetMapping("/reporte")
    public ResponseEntity<String> obtenerReporteMensual(
            @Parameter(description = "ID del aprendiz", required = true) @RequestParam Long aprendizId,
            @Parameter(description = "Mes del reporte (1-12)", required = true) @RequestParam int mes,
            @Parameter(description = "Año del reporte", required = true) @RequestParam int anio) {
        try {
            String reporte = actividadServicio.generarReporteMensual(aprendizId, mes, anio);
            return ResponseEntity.ok(reporte);
        } catch (ActividadNoEncontradaExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}