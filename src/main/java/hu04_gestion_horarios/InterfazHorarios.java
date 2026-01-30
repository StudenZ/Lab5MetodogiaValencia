package hu04_gestion_horarios;

import javax.swing.*;
import java.awt.*;

public class InterfazHorarios extends JFrame {

    public InterfazHorarios() {
        setTitle("Configuración Profesional");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        // 1. Header Azul Moderno
        JPanel header = new JPanel();
        header.setBackground(new Color(40, 53, 147)); // Azul Índigo Oscuro
        header.setBounds(0, 0, 550, 70);
        header.setLayout(null);
        add(header);

        JLabel title = new JLabel("Configuración de Jornada");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(20, 15, 300, 40);
        header.add(title);
        
        JLabel subtitle = new JLabel("Defina su disponibilidad semanal");
        subtitle.setForeground(new Color(197, 202, 233));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setBounds(22, 45, 300, 20);
        header.add(subtitle);

        // 2. Panel Blanco Contenido
        JPanel body = new JPanel();
        body.setBackground(Color.WHITE);
        body.setBounds(0, 70, 550, 430);
        body.setLayout(null);
        add(body);

        // Sección Días
        JLabel lblDias = new JLabel("Días Laborables");
        lblDias.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDias.setForeground(new Color(66, 66, 66));
        lblDias.setBounds(30, 20, 200, 20);
        body.add(lblDias);

        // Checkboxes estilizados
        String[] dias = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        int x = 30;
        for (String d : dias) {
            JCheckBox cb = new JCheckBox(d);
            cb.setBackground(new Color(245, 245, 245)); // Gris muy claro
            cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            cb.setBorderPainted(true);
            cb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            cb.setBounds(x, 50, 60, 40);
            cb.setHorizontalAlignment(SwingConstants.CENTER);
            if(!d.equals("Sáb")) cb.setSelected(true); // Pre-seleccionar L-V
            body.add(cb);
            x += 75;
        }

        // Separador visual
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 110, 470, 10);
        body.add(sep);

        // Sección Horas (Diseño Grid)
        JLabel lblHoras = new JLabel("Franja Horaria");
        lblHoras.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHoras.setForeground(new Color(66, 66, 66));
        lblHoras.setBounds(30, 130, 200, 20);
        body.add(lblHoras);

        // Panel interno para las horas
        JPanel pnlHoras = new JPanel();
        pnlHoras.setBackground(new Color(232, 240, 254)); // Azul muy pálido
        pnlHoras.setBounds(30, 160, 470, 80);
        pnlHoras.setLayout(null);
        pnlHoras.setBorder(BorderFactory.createLineBorder(new Color(187, 222, 251)));
        body.add(pnlHoras);

        pnlHoras.add(crearInputHora("Hora Inicio:", "08:00 AM", 20));
        pnlHoras.add(crearInputHora("Hora Fin:", "05:00 PM", 250));

        // Duración de turno
        JLabel lblDuracion = new JLabel("Duración por consulta:");
        lblDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDuracion.setBounds(30, 270, 150, 30);
        body.add(lblDuracion);

        JComboBox<String> cmbDuracion = new JComboBox<>(new String[]{"15 min", "20 min", "30 min", "45 min", "60 min"});
        cmbDuracion.setBounds(180, 270, 150, 30);
        body.add(cmbDuracion);

        // Botón Guardar (Azul)
        JButton btnGuardar = new JButton("GUARDAR CAMBIOS");
        btnGuardar.setBackground(new Color(40, 53, 147));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setBounds(125, 330, 300, 45);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Configuración guardada correctamente.\nSe han actualizado los cupos disponibles.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        
        body.add(btnGuardar);
    }

    private JPanel crearInputHora(String label, String valor, int x) {
        JPanel p = new JPanel(null);
        p.setOpaque(false);
        p.setBounds(x, 10, 200, 60);

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(25, 118, 210));
        l.setBounds(0, 5, 100, 20);
        p.add(l);

        JTextField t = new JTextField(valor);
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        t.setBounds(0, 30, 120, 30);
        p.add(t);

        return p;
    }

    public static void main(String[] args) {
        new InterfazHorarios().setVisible(true);
    }
}