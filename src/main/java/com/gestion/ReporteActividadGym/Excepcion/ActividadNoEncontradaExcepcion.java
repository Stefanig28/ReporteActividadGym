package com.gestion.ReporteActividadGym.Excepcion;

public class ActividadNoEncontradaExcepcion extends RuntimeException {
    public ActividadNoEncontradaExcepcion(String id) {
        super("La actividad " + id + " no se encuentra registrada.");
    }
}
