package base_datos;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionDB {
    
    // Si usas MongoDB local (instalado en tu PC):
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    
    // Si usas MongoDB Atlas (Nube), pon tu link aquí:
    // private static final String CONNECTION_STRING = "mongodb+srv://usuario:password@cluster...";

    private static final String DATABASE_NAME = "gestion_citas_db";

    public static MongoDatabase getDatabase() {
        try {
            MongoClient client = MongoClients.create(CONNECTION_STRING);
            return client.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            System.err.println("❌ Error conectando a MongoDB: " + e.getMessage());
            return null;
        }
    }
}