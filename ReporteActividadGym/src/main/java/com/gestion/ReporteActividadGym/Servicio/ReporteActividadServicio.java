package com.gestion.ReporteActividadGym.Servicio;

import com.gestion.ReporteActividadGym.Modelo.ReporteActividad;
import com.gestion.ReporteActividadGym.Repositorio.ReporteActividadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteActividadServicio {

    private final ReporteActividadRepositorio reporteActividadRepositorio;

    @Autowired
    public ReporteActividadServicio(ReporteActividadRepositorio reporteActividadRepositorio) {
        this.reporteActividadRepositorio = reporteActividadRepositorio;
    }

    public void guardarActividad(ReporteActividad actividad) {
        reporteActividadRepositorio.save(actividad);
    }

    public List<ReporteActividad> obtenerActividades() {
        return reporteActividadRepositorio.findAll();
    }

    public List<ReporteActividad> obtenerActividadesPorAprendiz(String aprendizId) {
        return reporteActividadRepositorio.findByAprendizId(aprendizId);
    }

}

