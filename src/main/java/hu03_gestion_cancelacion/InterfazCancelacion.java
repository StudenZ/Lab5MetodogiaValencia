package hu03_gestion_cancelacion;

import base_datos.ConexionDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId; // Importante para identificar la cita única

import javax.swing.*;
import java.awt.*;

public class InterfazCancelacion extends JFrame {

    private JPanel panelContenedor;
    private int posicionY = 25; 

    public InterfazCancelacion() {
        setTitle("Mis Reservas - HU-03 (Funcional)");
        setSize(720, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // O DISPOSE si es ventana secundaria
        setLocationRelativeTo(null);
        setResizable(false);
        
        // 1. HEADER 
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 101, 192)); // Azul Profesional
        header.setPreferredSize(new Dimension(720, 85));
        header.setLayout(null);
        
        JLabel titulo = new JLabel("Mis Citas Programadas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBounds(30, 20, 300, 30);
        header.add(titulo);
        
        JLabel subtitulo = new JLabel("Política: Solo puede cancelar citas con estado 'PENDIENTE'.");
        subtitulo.setForeground(new Color(227, 242, 253));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setBounds(30, 50, 500, 20);
        header.add(subtitulo);

        // Botón Refrescar
        JButton btnRefrescar = new JButton("🔄");
        btnRefrescar.setBounds(650, 25, 45, 35);
        btnRefrescar.setBackground(new Color(25, 118, 210));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarCitasUsuario());
        header.add(btnRefrescar);
        
        add(header, BorderLayout.NORTH);

        // 2. PANEL CONTENEDOR
        panelContenedor = new JPanel();
        panelContenedor.setLayout(null); 
        panelContenedor.setBackground(new Color(245, 247, 250));
        panelContenedor.setPreferredSize(new Dimension(680, 800)); 

        JScrollPane scroll = new JScrollPane(panelContenedor);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Cargar datos reales
        cargarCitasUsuario();
    }

    // --- LÓGICA MONGODB ---
    private void cargarCitasUsuario() {
        panelContenedor.removeAll(); // Limpiar visualmente
        posicionY = 25; // Reiniciar altura
        
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            if (db != null) {
                MongoCollection<Document> col = db.getCollection("citas");
                
                // FILTRO: Solo mostramos citas PENDIENTES del usuario.
                // (Si quieres ver todas para probar, quita el filtro de estado)
                FindIterable<Document> misCitas = col.find(
                    Filters.and(
                        Filters.eq("estado", "PENDIENTE") 
                        // Filters.eq("paciente", "Usuario Actual") // Si tuvieras login real
                    )
                );
                
                boolean hayCitas = false;
                for (Document doc : misCitas) {
                    hayCitas = true;
                    agregarTarjeta(
                        doc.getObjectId("_id"), // ID ÚNICO DE MONGODB
                        doc.getString("fecha"), 
                        doc.getString("especialidad"), 
                        doc.getString("medico"), 
                        doc.getString("hora")
                    );
                }

                if (!hayCitas) {
                    JLabel vacio = new JLabel("No tienes citas pendientes para cancelar.");
                    vacio.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                    vacio.setForeground(Color.GRAY);
                    vacio.setBounds(180, 100, 400, 30);
                    panelContenedor.add(vacio);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    private void agregarTarjeta(ObjectId idCita, String fecha, String especialidad, String medico, String hora) {
        
        int anchoTarjeta = 640;
        int altoTarjeta = 115;
        
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBounds(30, posicionY, anchoTarjeta, altoTarjeta);
        card.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 1));
        
        // Barra lateral azul (Indicador de activo)
        JPanel barraLateral = new JPanel();
        barraLateral.setBackground(new Color(33, 150, 243));
        barraLateral.setBounds(0, 0, 6, 115);
        card.add(barraLateral);

        // FECHA (Asumimos formato texto simple para el ejemplo)
        JLabel lblFecha = new JLabel("📅 " + fecha);
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFecha.setForeground(new Color(33, 33, 33));
        lblFecha.setBounds(25, 20, 150, 25);
        card.add(lblFecha);

        JLabel lblEsp = new JLabel(especialidad);
        lblEsp.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblEsp.setForeground(new Color(21, 101, 192));
        lblEsp.setBounds(25, 45, 300, 25);
        card.add(lblEsp);

        JLabel lblDetalle = new JLabel(medico + "  •  " + hora);
        lblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDetalle.setForeground(Color.GRAY);
        lblDetalle.setBounds(25, 75, 300, 20);
        card.add(lblDetalle);

        // --- BOTÓN CANCELAR ---
        JButton btnCancelar = new JButton("Cancelar Cita");
        btnCancelar.setBounds(480, 38, 130, 40); 
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(new Color(211, 47, 47)); // Rojo
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(211, 47, 47), 1));
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCancelar.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que deseas cancelar esta cita?\nEl médico será notificado inmediatamente.", 
                "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
            
            if (opt == JOptionPane.YES_OPTION) {
                // ACCIÓN EN MONGODB: UPDATE estado -> CANCELADO
                cancelarEnBaseDeDatos(idCita);
                
                // Efecto visual inmediato
                barraLateral.setBackground(Color.GRAY);
                btnCancelar.setEnabled(false);
                btnCancelar.setText("Cancelada");
                JOptionPane.showMessageDialog(this, "Cita cancelada correctamente.");
                
                // Recargar lista para que desaparezca (o dejarla ahí deshabilitada)
                cargarCitasUsuario(); 
            }
        });

        card.add(btnCancelar);
        panelContenedor.add(card);
        
        posicionY += 130; 
        panelContenedor.setPreferredSize(new Dimension(600, posicionY + 20));
    }

    private void cancelarEnBaseDeDatos(ObjectId id) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            if (db != null) {
                MongoCollection<Document> col = db.getCollection("citas");
                // Buscamos por ID único (_id) y actualizamos estado
                col.updateOne(Filters.eq("_id", id), Updates.set("estado", "CANCELADO"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cancelar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new InterfazCancelacion().setVisible(true);
    }
}