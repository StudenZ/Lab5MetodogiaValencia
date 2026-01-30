package base_datos;

import org.bson.Document;

public class Cita {
    private String especialidad;
    private String medico;
    private String fecha;
    private String hora;
    private String paciente;
    private String estado; // "PENDIENTE", "CANCELADA", "FINALIZADA"

    public Cita(String especialidad, String medico, String fecha, String hora, String paciente, String estado) {
        this.especialidad = especialidad;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.estado = estado;
    }

    // Convertir este objeto Java a un Documento MongoDB
    public Document toDocument() {
        return new Document("especialidad", especialidad)
                .append("medico", medico)
                .append("fecha", fecha)
                .append("hora", hora)
                .append("paciente", paciente)
                .append("estado", estado);
    }
    
    // Getters necesarios
    public String getEspecialidad() { return especialidad; }
    public String getMedico() { return medico; }
    public String getHora() { return hora; }
    public String getPaciente() { return paciente; }
    public String getEstado() { return estado; }
}