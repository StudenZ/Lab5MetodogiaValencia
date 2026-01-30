package hu01_agendar_cita;

import base_datos.ConexionDB; // Importamos tu clase de conexión
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // Para manejar la lista de botones

public class InterfazAgendar extends JFrame {

    // Variables para guardar los datos seleccionados
    private String horaSeleccionada = ""; 
    private ArrayList<JButton> botonesHora; // Lista para controlar los botones visualmente

    public InterfazAgendar() {
        setTitle("Reserva de Turnos - HU-01 (Funcional)");
        setSize(500, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        botonesHora = new ArrayList<>();

        // FONDO GENERAL
        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(245, 247, 250));
        fondo.setLayout(null);
        add(fondo);

        // 1. HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(21, 101, 192));
        header.setBounds(0, 0, 500, 70);
        header.setLayout(null);
        
        JLabel titulo = new JLabel("Nueva Cita Médica");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBounds(20, 20, 300, 30);
        header.add(titulo);
        fondo.add(header);

        // 2. TARJETA DE FORMULARIO
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBounds(25, 90, 435, 520);
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        fondo.add(card);

        // --- SELECCIÓN DE ESPECIALIDAD ---
        JLabel lblEsp = new JLabel("1. Especialidad y Médico");
        lblEsp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEsp.setForeground(new Color(66, 66, 66));
        lblEsp.setBounds(25, 20, 200, 20);
        card.add(lblEsp);

        JComboBox<String> cmbEspecialidad = new JComboBox<>(new String[]{"Medicina General", "Cardiología", "Pediatría", "Dermatología"});
        estilizarCombo(cmbEspecialidad, 50);
        card.add(cmbEspecialidad);

        JComboBox<String> cmbMedico = new JComboBox<>(new String[]{"Dr. Alejandro Velasco", "Dra. María Paz", "Dr. Juan Ruiz"});
        estilizarCombo(cmbMedico, 95);
        card.add(cmbMedico);

        // --- SELECCIÓN DE FECHA ---
        JLabel lblFecha = new JLabel("2. Fecha Preferida");
        lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFecha.setForeground(new Color(66, 66, 66));
        lblFecha.setBounds(25, 150, 200, 20);
        card.add(lblFecha);

        JTextField txtFecha = new JTextField("19/01/2026");
        txtFecha.setBounds(25, 180, 385, 40);
        txtFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFecha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 5)));
        card.add(txtFecha);

        // --- SELECCIÓN DE HORA ---
        JLabel lblHora = new JLabel("3. Horarios Disponibles (Seleccione uno)");
        lblHora.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHora.setForeground(new Color(66, 66, 66));
        lblHora.setBounds(25, 240, 300, 20);
        card.add(lblHora);

        JPanel panelHoras = new JPanel(new GridLayout(2, 3, 10, 10));
        panelHoras.setBackground(Color.WHITE);
        panelHoras.setBounds(25, 270, 385, 90);
        
        // Creamos botones funcionales
        panelHoras.add(crearBotonHora("08:00 AM"));
        panelHoras.add(crearBotonHora("09:30 AM"));
        panelHoras.add(crearBotonHora("10:00 AM"));
        panelHoras.add(crearBotonHora("11:30 AM"));
        panelHoras.add(crearBotonHora("02:00 PM"));
        panelHoras.add(crearBotonHora("04:00 PM"));
        
        card.add(panelHoras);

        // --- BOTÓN CONFIRMAR (CON LÓGICA MONGODB) ---
        JButton btnConfirmar = new JButton("CONFIRMAR RESERVA");
        btnConfirmar.setBounds(25, 430, 385, 50);
        btnConfirmar.setBackground(new Color(0, 150, 136)); 
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // AQUI ESTÁ LA MAGIA DE MONGODB
        btnConfirmar.addActionListener(e -> {
            // 1. Validar campos
            if(horaSeleccionada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una hora.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Obtener datos del formulario
            String especialidad = (String) cmbEspecialidad.getSelectedItem();
            String medico = (String) cmbMedico.getSelectedItem();
            String fecha = txtFecha.getText();
            
            // 3. Insertar en MongoDB
            try {
                MongoDatabase db = ConexionDB.getDatabase();
                if (db != null) {
                    MongoCollection<Document> citasCol = db.getCollection("citas");
                    
                    Document nuevaCita = new Document("especialidad", especialidad)
                            .append("medico", medico)
                            .append("fecha", fecha)
                            .append("hora", horaSeleccionada)
                            .append("paciente", "Usuario Actual") // Simulamos el usuario logueado
                            .append("estado", "PENDIENTE");

                    citasCol.insertOne(nuevaCita);

                    JOptionPane.showMessageDialog(this, "¡Cita agendada exitosamente en MongoDB!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Limpiar selección visual
                    horaSeleccionada = "";
                    limpiarEstilosBotones();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });
        
        card.add(btnConfirmar);
    }

    private void estilizarCombo(JComboBox box, int y) {
        box.setBounds(25, y, 385, 40);
        box.setBackground(Color.WHITE);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private JButton crearBotonHora(String hora) {
        JButton btn = new JButton(hora);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(33, 33, 33));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Acción al hacer clic en una hora
        btn.addActionListener(e -> {
            horaSeleccionada = hora; // Guardamos la hora en la variable
            limpiarEstilosBotones(); // Reseteamos los otros botones
            
            // Resaltamos este botón
            btn.setBackground(new Color(224, 242, 241));
            btn.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 136), 2));
        });
        
        botonesHora.add(btn); // Lo agregamos a la lista para controlarlo luego
        return btn;
    }
    
    // Método auxiliar para quitar el color de selección a todos los botones
    private void limpiarEstilosBotones() {
        for(JButton b : botonesHora) {
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new InterfazAgendar().setVisible(true);
    }
}