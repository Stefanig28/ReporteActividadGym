package com.gestion.ReporteActividadGym.Excepcion;

public class ActividadExistenteExcepcion extends RuntimeException {
    public ActividadExistenteExcepcion(Long aprendizId) {
        super("El aprendiz " + aprendizId + " ya ha registrado un entrenamiento para esta fecha con los mismos parámetros.");
    }
}
