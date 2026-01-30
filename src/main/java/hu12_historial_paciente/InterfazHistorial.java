package hu12_historial_paciente;

import javax.swing.*;
import java.awt.*;

public class InterfazHistorial extends JFrame {
    public InterfazHistorial() {
        setTitle("Mi Historial Médico");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(new Color(245, 245, 245));
        
        JLabel titulo = new JLabel("Consultas Anteriores");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenedor.add(titulo);

        // Agregamos un par de tarjetas de ejemplo
        contenedor.add(crearCard("15 Ene 2024", "Dr. Pérez - General", "Completada"));
        contenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        contenedor.add(crearCard("02 Dic 2023", "Dra. Gómez - Dental", "Completada"));
        contenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        contenedor.add(crearCard("10 Oct 2023", "Dr. Ruiz - Cardio", "Cancelada"));

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        add(scroll);
    }

    private JPanel crearCard(String fecha, String medico, String estado) {
        JPanel card = new JPanel();
        card.setMaximumSize(new Dimension(350, 100));
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(3, 1));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel f = new JLabel(fecha);
        f.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel m = new JLabel(medico);
        JLabel e = new JLabel("Estado: " + estado);
        e.setForeground(estado.equals("Cancelada") ? Color.RED : new Color(46, 125, 50));

        card.add(f); card.add(m); card.add(e);
        return card;
    }

    public static void main(String[] args) {
        new InterfazHistorial().setVisible(true);
    }
}