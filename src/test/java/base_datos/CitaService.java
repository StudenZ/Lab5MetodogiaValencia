package base_datos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class CitaService {
    private final MongoCollection<Document> coleccion;

    public CitaService() {
        MongoDatabase db = ConexionDB.getDatabase();
        this.coleccion = (db != null) ? db.getCollection("citas") : null;
    }

    // Metodo para agendar (Create) - Paso 5 de la guia
    public boolean crearCita(Cita cita) {
        if (coleccion == null || horarioOcupado(cita.getMedico(), cita.getHora())) {
            return false; 
        }
        coleccion.insertOne(cita.toDocument());
        return true;
    }

    // Validacion de solapamiento (HU-05) - Punto critico de la practica
    public boolean horarioOcupado(String medico, String hora) {
        Document query = new Document("medico", medico).append("hora", hora);
        return coleccion.find(query).first() != null;
    }

    // Actualizar estado (Update)
    public void actualizarEstado(String paciente, String hora, String nuevoEstado) {
        if (coleccion != null) {
            coleccion.updateOne(
                Filters.and(Filters.eq("paciente", paciente), Filters.eq("hora", hora)),
                new Document("$set", new Document("estado", nuevoEstado))
            );
        }
    }
}