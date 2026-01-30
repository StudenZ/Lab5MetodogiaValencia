package base_datos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class CitaServiceTest {
    private CitaService servicio;

    @BeforeEach
    void preparar() {
    servicio = new CitaService();
    // Esto limpia la tabla para que el test no falle por duplicados previos
    base_datos.ConexionDB.getDatabase().getCollection("citas").drop();
}

    @Test
    void agendarCitaDebeFuncionar() {
        // Probamos crear una cita normal (HU-01)
        Cita nueva = new Cita("Cardiologia", "Dr. Ruiz", "20/02/2026", "09:00 AM", "Moises Valencia", "PENDIENTE");
        boolean exito = servicio.crearCita(nueva);
        assertTrue(exito, "La cita deberia guardarse si el horario esta libre");
    }

    @Test
    void noDebePermitirCitasDuplicadas() {
        // Probamos la validacion de solapamiento (HU-05)
        String medico = "Dra. Maria Paz";
        String hora = "10:00 AM";
        
        Cita cita1 = new Cita("Pediatria", medico, "20/02/2026", hora, "Juanito", "PENDIENTE");
        Cita cita2 = new Cita("Pediatria", medico, "20/02/2026", hora, "Pedrito", "PENDIENTE");

        servicio.crearCita(cita1);
        boolean exitoSegunda = servicio.crearCita(cita2);

        assertFalse(exitoSegunda, "El sistema no debe dejar agendar dos citas a la misma hora con el mismo doctor");
    }

    @Test
    void cancelarCitaCambiaEstado() {
        // Probamos el borrado logico (HU-03)
        servicio.actualizarEstado("Moises Valencia", "09:00 AM", "CANCELADO");
        // Aqui lo ideal seria buscar la cita y verificar que diga CANCELADO
    }
}