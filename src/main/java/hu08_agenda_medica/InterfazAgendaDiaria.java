package hu08_agenda_medica;

import base_datos.ConexionDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InterfazAgendaDiaria extends JFrame {

    private JPanel panelPacientes;

    public InterfazAgendaDiaria() {
        setTitle("Agenda Médica Interactiva - HU-08");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(245, 247, 250));
        add(fondo);

        // --- HEADER ---
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(950, 80));
        header.setLayout(new BorderLayout()); // Layout más seguro
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(224, 224, 224)));
        
        // Panel Header Izquierdo (Textos)
        JPanel headerLeft = new JPanel(new GridLayout(2, 1));
        headerLeft.setBackground(Color.WHITE);
        headerLeft.setBorder(new EmptyBorder(10, 30, 10, 0));
        
        JLabel lblDoc = new JLabel("Dr. Alejandro Velasco");
        lblDoc.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLeft.add(lblDoc);

        JLabel lblEsp = new JLabel("Cardiología • Consultorio 402");
        lblEsp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEsp.setForeground(Color.GRAY);
        headerLeft.add(lblEsp);
        
        header.add(headerLeft, BorderLayout.WEST);

        // Panel Header Derecho (Botón Recargar)
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 20));
        headerRight.setBackground(Color.WHITE);
        
        JButton btnRefresh = new JButton("🔄 Recargar Lista");
        btnRefresh.setPreferredSize(new Dimension(150, 40));
        btnRefresh.setBackground(new Color(33, 150, 243));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> cargarCitasDesdeBD());
        
        headerRight.add(btnRefresh);
        header.add(headerRight, BorderLayout.EAST);

        fondo.add(header, BorderLayout.NORTH);

        // --- LISTA SCROLL ---
        panelPacientes = new JPanel();
        panelPacientes.setLayout(new BoxLayout(panelPacientes, BoxLayout.Y_AXIS));
        panelPacientes.setBackground(new Color(245, 247, 250));
        panelPacientes.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(panelPacientes);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        fondo.add(scroll, BorderLayout.CENTER);

        cargarCitasDesdeBD();
    }

    private void cargarCitasDesdeBD() {
        panelPacientes.removeAll();
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            if (db != null) {
                MongoCollection<Document> col = db.getCollection("citas");
                FindIterable<Document> citas = col.find();

                boolean hayCitas = false;
                for (Document doc : citas) {
                    hayCitas = true;
                    panelPacientes.add(crearFilaPaciente(
                        doc.getString("hora"), 
                        doc.getString("paciente"), 
                        doc.getString("especialidad"), 
                        doc.getString("estado")
                    ));
                    // Espacio entre tarjetas
                    panelPacientes.add(Box.createRigidArea(new Dimension(0, 10)));
                }
                
                if(!hayCitas) {
                    panelPacientes.add(new JLabel("No hay citas en la base de datos."));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        panelPacientes.revalidate();
        panelPacientes.repaint();
    }

    private void cambiarEstadoCita(String paciente, String hora, String nuevoEstado) {
        try {
            MongoDatabase db = ConexionDB.getDatabase();
            if (db != null) {
                MongoCollection<Document> col = db.getCollection("citas");
                col.updateOne(
                    Filters.and(Filters.eq("paciente", paciente), Filters.eq("hora", hora)),
                    Updates.set("estado", nuevoEstado)
                );
                cargarCitasDesdeBD(); 
                JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private JPanel crearFilaPaciente(String hora, String nombre, String motivo, String estado) {
        // Normalizar Estado
        if (estado == null || estado.trim().isEmpty()) estado = "PENDIENTE";

        // --- PANEL PRINCIPAL DE LA FILA (Usamos BorderLayout para anclar cosas) ---
        JPanel panelFila = new JPanel(new BorderLayout());
        panelFila.setBackground(Color.WHITE);
        panelFila.setPreferredSize(new Dimension(850, 80));
        panelFila.setMaximumSize(new Dimension(850, 80));
        
        // Colores
        Color colorBorde;
        Color bgBadge;
        Color txtBadge;
        boolean mostrarBotones = false;

        switch (estado) {
            case "FINALIZADO":
                colorBorde = new Color(76, 175, 80);
                bgBadge = new Color(232, 245, 233);
                txtBadge = new Color(27, 94, 32);
                break;
            case "NO ASISTIÓ":
            case "CANCELADO":
                colorBorde = new Color(244, 67, 54);
                bgBadge = new Color(255, 235, 238);
                txtBadge = new Color(183, 28, 28);
                break;
            default: // PENDIENTE
                colorBorde = new Color(255, 193, 7);
                bgBadge = new Color(255, 248, 225);
                txtBadge = new Color(230, 81, 0);
                mostrarBotones = true;
                estado = "PENDIENTE";
                break;
        }

        panelFila.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, colorBorde));

        // --- 1. IZQUIERDA: DATOS DEL PACIENTE (Usamos Null Layout solo aquí para precisión) ---
        JPanel panelInfo = new JPanel(null);
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setPreferredSize(new Dimension(500, 80)); // Ocupa la mitad izquierda

        JLabel lblHora = new JLabel(hora != null ? hora : "--:--");
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHora.setForeground(Color.DARK_GRAY);
        lblHora.setBounds(20, 25, 90, 30);
        panelInfo.add(lblHora);

        JLabel lblNombre = new JLabel(nombre != null ? nombre : "Paciente");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setBounds(120, 15, 250, 25);
        panelInfo.add(lblNombre);

        JLabel lblMotivo = new JLabel(motivo != null ? motivo : "Consulta");
        lblMotivo.setForeground(Color.GRAY);
        lblMotivo.setBounds(120, 40, 250, 20);
        panelInfo.add(lblMotivo);

        // Badge Estado
        JLabel lblEstado = new JLabel(estado, SwingConstants.CENTER);
        lblEstado.setOpaque(true);
        lblEstado.setBackground(bgBadge);
        lblEstado.setForeground(txtBadge);
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEstado.setBounds(380, 25, 100, 30);
        panelInfo.add(lblEstado);

        // Agregamos info a la izquierda
        panelFila.add(panelInfo, BorderLayout.CENTER);

        // --- 2. DERECHA: BOTONES (Usamos FlowLayout para que se acomoden solos) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20)); // Alineado derecha, Padding
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setPreferredSize(new Dimension(250, 80));

        if (mostrarBotones) {
            JButton btnAtender = new JButton("Atender");
            estilizarBoton(btnAtender, new Color(232, 245, 233), new Color(46, 125, 50));
            // Acción Final: Usar variables finales para el lambda
            String finalNombre = nombre; 
            String finalHora = hora;
            btnAtender.addActionListener(e -> cambiarEstadoCita(finalNombre, finalHora, "FINALIZADO"));
            panelBotones.add(btnAtender);

            JButton btnAusente = new JButton("Ausente");
            estilizarBoton(btnAusente, new Color(255, 235, 238), new Color(198, 40, 40));
            btnAusente.addActionListener(e -> cambiarEstadoCita(finalNombre, finalHora, "NO ASISTIÓ"));
            panelBotones.add(btnAusente);
        }

        // Agregamos botones a la derecha
        panelFila.add(panelBotones, BorderLayout.EAST);

        return panelFila;
    }

    private void estilizarBoton(JButton btn, Color bg, Color fg) {
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createLineBorder(fg));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new InterfazAgendaDiaria().setVisible(true);
    }
}