package com.gestion.ReporteActividadGym.Servicio;

import com.gestion.ReporteActividadGym.Excepcion.ActividadExistenteExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.ActividadNoEncontradaExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.ActividadNoExistenteExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.AprendizNoEncontradoExcepcion;
import com.gestion.ReporteActividadGym.Modelo.Actividad;
import com.gestion.ReporteActividadGym.Repositorio.ActividadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class ActividadServicio {

    private final ActividadRepositorio actividadRepositorio;

    @Autowired
    public ActividadServicio(ActividadRepositorio actividadRepositorio) {
        this.actividadRepositorio = actividadRepositorio;
    }

    public void crearActividad(Actividad actividad) {
        List<Actividad> actividadExiste = actividadRepositorio.findByAprendizIdAndFechaEntrenamientoAndNombreEntrenamientoAndTipoEntrenamientoAndDuracionEntrenamiento(
                actividad.getAprendizId(),
                actividad.getFechaEntrenamiento(),
                actividad.getNombreEntrenamiento(),
                actividad.getTipoEntrenamiento(),
                actividad.getDuracionEntrenamiento()
        );

        if (!actividadExiste.isEmpty()) {
            throw new ActividadExistenteExcepcion(actividad.getAprendizId());
        }
        actividadRepositorio.save(actividad);
    }

    public List<Actividad> obtenerActividades() {
        if (actividadRepositorio == null) {
            throw new ActividadNoExistenteExcepcion();
        }
        return actividadRepositorio.findAll();
    }

    public List<Actividad> obtenerActividadesPorAprendiz(Long aprendizId) {
        List<Actividad> actividades = actividadRepositorio.findByAprendizId(aprendizId);
        if (actividades.isEmpty()) {
            throw new AprendizNoEncontradoExcepcion(aprendizId);
        }
        return actividades;
    }

    public void actualizarActividad(String id, Actividad actividad) {
        Actividad actividadActualizar = actividadRepositorio.findById(id)
                .orElseThrow(() -> new ActividadNoEncontradaExcepcion(id));

        actividadActualizar.setAprendizId(actividad.getAprendizId());
        actividadActualizar.setEntrenadorId(actividad.getEntrenadorId());
        actividadActualizar.setNombreEntrenamiento(actividad.getNombreEntrenamiento());
        actividadActualizar.setFechaEntrenamiento(actividad.getFechaEntrenamiento());
        actividadActualizar.setTipoEntrenamiento(actividad.getTipoEntrenamiento());
        actividadActualizar.setDuracionEntrenamiento(actividad.getDuracionEntrenamiento());

        actividadRepositorio.save(actividadActualizar);
    }

    public void eliminarActividad(String id) {
        actividadRepositorio.findById(id).orElseThrow(() -> new ActividadNoEncontradaExcepcion(id));
    }

    public String generarReporteMensual(Long aprendizId, int mes, int anio) {
        List<Actividad> actividades = obtenerActividadesPorAprendiz(aprendizId);

        if (actividades.isEmpty()) {
            return "El aprendiz " + aprendizId + " no tiene actividades registradas para el mes " + mes + " del año " + anio + ".";
        }
        LocalDate inicioMes = LocalDate.of(anio, mes, 1);
        LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        // Se mapean las actividades de las semanas de cada mes
        Map<Integer, List<Actividad>> actividadesPorSemana = new LinkedHashMap<>();
        for (int i = 1; i <= 4; i++) {
            actividadesPorSemana.put(i, new ArrayList<>());
        }

        // Clasificamos las actividades por cada semana de los meses
        for (Actividad actividad : actividades) {
            LocalDate fecha = actividad.getFechaEntrenamiento();
            if (fecha.isBefore(inicioMes) || fecha.isAfter(finMes)) {
                continue;
            }

            int semanaDelMes = (fecha.getDayOfMonth() - 1) / 7 + 1;
            actividadesPorSemana.get(semanaDelMes).add(actividad);
        }

        // Construimos el reporte
        StringBuilder reporte = new StringBuilder();
        reporte.append(String.format("Reporte Mensual del Aprendiz %d (%s %d):\n", aprendizId, inicioMes.getMonth(), anio));

        for (int semana = 1; semana <= 4; semana++) {
            reporte.append(String.format("\nSemana %d:\n", semana));
            List<Actividad> actividadesSemana = actividadesPorSemana.getOrDefault(semana, Collections.emptyList());

            // Ordenamos las actividades por fecha
            actividadesSemana.sort(Comparator.comparing(Actividad::getFechaEntrenamiento));

            for (Actividad actividad : actividadesSemana) {
                DayOfWeek diaDeLaSemana = actividad.getFechaEntrenamiento().getDayOfWeek();
                reporte.append(String.format("- %s: %s, %s\n", diaDeLaSemana, actividad.getFechaEntrenamiento(), actividad.getNombreEntrenamiento()));
            }
        }

        return reporte.toString();
    }
}

