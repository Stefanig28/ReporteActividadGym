package com.gestion.ReporteActividadGym.Excepcion;

public class AprendizNoEncontradoExcepcion extends RuntimeException {
    public AprendizNoEncontradoExcepcion(Long aprendizId) {
        super("El aprendiz " + aprendizId + " no se encuentra registrado.");
    }
}
