package com.gestion.ReporteActividadGym;

import com.gestion.ReporteActividadGym.Excepcion.ActividadExistenteExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.ActividadNoEncontradaExcepcion;
import com.gestion.ReporteActividadGym.Excepcion.AprendizNoEncontradoExcepcion;
import com.gestion.ReporteActividadGym.Modelo.Actividad;
import com.gestion.ReporteActividadGym.Repositorio.ActividadRepositorio;
import com.gestion.ReporteActividadGym.Servicio.ActividadServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActividadServicioTest {
    private ActividadRepositorio actividadRepositorio;
    private ActividadServicio actividadServicio;

    @BeforeEach
    public void setUp() {
        this.actividadRepositorio = mock(ActividadRepositorio.class);
        this.actividadServicio = new ActividadServicio(actividadRepositorio);
    }

    @Test
    public void crearActividadExistenteTest() {
        Actividad actividad = new Actividad();

        when(actividadRepositorio.findByAprendizIdAndFechaEntrenamientoAndNombreEntrenamientoAndTipoEntrenamientoAndDuracionEntrenamiento(
                actividad.getAprendizId(),
                actividad.getFechaEntrenamiento(),
                actividad.getNombreEntrenamiento(),
                actividad.getTipoEntrenamiento(),
                actividad.getDuracionEntrenamiento()
        )).thenReturn(Collections.singletonList(actividad));

        assertThrows(ActividadExistenteExcepcion.class, () -> actividadServicio.crearActividad(actividad));
    }

    @Test
    public void crearActividadNuevaTest() {
        Actividad actividad = new Actividad();

        when(actividadRepositorio.findByAprendizIdAndFechaEntrenamientoAndNombreEntrenamientoAndTipoEntrenamientoAndDuracionEntrenamiento(
                actividad.getAprendizId(),
                actividad.getFechaEntrenamiento(),
                actividad.getNombreEntrenamiento(),
                actividad.getTipoEntrenamiento(),
                actividad.getDuracionEntrenamiento()
        )).thenReturn(Collections.emptyList());

        actividadServicio.crearActividad(actividad);

        verify(actividadRepositorio).save(actividad);

    }

    @Test
    public void obtenerActividadesNoExistentesTest() {

        when(actividadRepositorio.findAll()).thenReturn(Collections.emptyList());

        List<Actividad> actividades = actividadServicio.obtenerActividades();
        assertTrue(actividades.isEmpty());
    }

    @Test
    public void obtenerActividadesPorAprendiz_ActividadNoExistente() {
        Long aprendizId = 1L;
        when(actividadRepositorio.findByAprendizId(aprendizId)).thenReturn(Collections.emptyList());

        assertThrows(AprendizNoEncontradoExcepcion.class, () -> actividadServicio.obtenerActividadesPorAprendiz(aprendizId));
    }

    @Test
    public void actualizarActividadTest() {

        String id = "1";
        Actividad actividadExistente = new Actividad();
        Actividad actividadActualizada = new Actividad();


        actividadActualizada.setAprendizId(4L);
        actividadActualizada.setEntrenadorId(2L);
        actividadActualizada.setNombreEntrenamiento("Pecho");
        actividadActualizada.setFechaEntrenamiento(LocalDate.now());
        actividadActualizada.setTipoEntrenamiento("Pecho banco plano 10x4");
        actividadActualizada.setDuracionEntrenamiento("1:00");

        when(actividadRepositorio.findById(id)).thenReturn(Optional.of(actividadExistente));
        actividadServicio.actualizarActividad(id, actividadActualizada);

        verify(actividadRepositorio).save(actividadExistente);
    }

    @Test
    public void eliminarActividadNoEncontrada() {
        String id = "1";
        when(actividadRepositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(ActividadNoEncontradaExcepcion.class, () -> actividadServicio.eliminarActividad(id));
    }

    @Test
    public void generarReporteMensualTest() {
        long aprendizId = 1L;
        LocalDate fecha1 = LocalDate.of(2024, 2, 1);
        LocalDate fecha2 = LocalDate.of(2024, 2, 10);

        Actividad actividad1 = new Actividad();
        actividad1.setFechaEntrenamiento(fecha1);
        actividad1.setNombreEntrenamiento("Espalda");

        Actividad actividad2 = new Actividad();
        actividad2.setFechaEntrenamiento(fecha2);
        actividad2.setNombreEntrenamiento("Triceps");

        List<Actividad> actividades = Arrays.asList(actividad1,actividad2);
        when(actividadRepositorio.findByAprendizId(aprendizId)).thenReturn(actividades);

        String reporte = actividadServicio.generarReporteMensual(aprendizId,2,2024);
        assertFalse(reporte.contains("Reporte Mensual el Aprendiz 1 (FEBRUARY 2024)"));
        assertTrue(reporte.contains("Semana 1:"));
        assertTrue(reporte.contains("Semana 2:"));

    }
}
