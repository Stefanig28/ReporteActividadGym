package com.gestion.ReporteActividadGym.Excepcion;

public class ActividadNoExistenteExcepcion extends RuntimeException {
    public ActividadNoExistenteExcepcion() {
        super("No se han creado actividades.");
    }
}
