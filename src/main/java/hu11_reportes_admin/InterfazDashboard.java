package hu11_reportes_admin;

import javax.swing.*;
import java.awt.*;

public class InterfazDashboard extends JFrame {

    public InterfazDashboard() {
        setTitle("Panel de Control Administrativo - HU-11");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(236, 239, 241)); // Gris azulado
        fondo.setLayout(null);
        add(fondo);

        // SIDEBAR (Barra lateral oscura)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(38, 50, 56)); // Gris plomo oscuro
        sidebar.setBounds(0, 0, 200, 600);
        sidebar.setLayout(null);
        
        JLabel lblAdmin = new JLabel("ADMIN PANEL");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAdmin.setBounds(30, 30, 150, 30);
        sidebar.add(lblAdmin);
        
        agregarBotonMenu(sidebar, "📊  Resumen", 100, true);
        agregarBotonMenu(sidebar, "👥  Médicos", 150, false);
        agregarBotonMenu(sidebar, "🏥  Pacientes", 200, false);
        agregarBotonMenu(sidebar, "⚙️  Configuración", 250, false);
        
        fondo.add(sidebar);

        // CONTENIDO PRINCIPAL
        JLabel lblTitulo = new JLabel("Resumen de Enero 2026");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(55, 71, 79));
        lblTitulo.setBounds(230, 30, 400, 30);
        fondo.add(lblTitulo);

        // TARJETAS DE KPI (Datos clave)
        crearTarjetaKPI(fondo, "Citas Totales", "1,240", new Color(33, 150, 243), 230, 90);
        crearTarjetaKPI(fondo, "Atendidos", "980", new Color(76, 175, 80), 410, 90);
        crearTarjetaKPI(fondo, "Cancelaciones", "15%", new Color(244, 67, 54), 590, 90);

        // SECCIÓN GRÁFICA (Simulada con ProgressBars)
        JPanel panelGrafico = new JPanel();
        panelGrafico.setBackground(Color.WHITE);
        panelGrafico.setBounds(230, 220, 520, 300);
        panelGrafico.setLayout(null);
        panelGrafico.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        JLabel lblGraph = new JLabel("Rendimiento por Especialidad");
        lblGraph.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGraph.setBounds(20, 20, 300, 20);
        panelGrafico.add(lblGraph);

        crearBarra(panelGrafico, "Medicina General", 85, 70);
        crearBarra(panelGrafico, "Cardiología", 60, 130);
        crearBarra(panelGrafico, "Pediatría", 45, 190);
        crearBarra(panelGrafico, "Dermatología", 30, 250);

        fondo.add(panelGrafico);
    }

    private void agregarBotonMenu(JPanel panel, String texto, int y, boolean activo) {
        JLabel btn = new JLabel(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(activo ? Color.WHITE : new Color(176, 190, 197));
        btn.setBounds(30, y, 150, 30);
        panel.add(btn);
    }

    private void crearTarjetaKPI(JPanel parent, String titulo, String valor, Color color, int x, int y) {
        JPanel kpi = new JPanel();
        kpi.setBackground(Color.WHITE);
        kpi.setBounds(x, y, 160, 100);
        kpi.setLayout(null);
        // Borde superior de color
        kpi.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, color));
        
        JLabel t = new JLabel(titulo);
        t.setForeground(Color.GRAY);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.setBounds(15, 15, 100, 20);
        kpi.add(t);
        
        JLabel v = new JLabel(valor);
        v.setForeground(new Color(33, 33, 33));
        v.setFont(new Font("Segoe UI", Font.BOLD, 28));
        v.setBounds(15, 40, 130, 40);
        kpi.add(v);
        
        parent.add(kpi);
    }

    private void crearBarra(JPanel panel, String nombre, int porcentaje, int y) {
        JLabel lbl = new JLabel(nombre);
        lbl.setBounds(20, y, 150, 20);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(lbl);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(porcentaje);
        barra.setBounds(20, y + 25, 400, 10);
        barra.setForeground(new Color(33, 150, 243));
        barra.setBackground(new Color(240, 240, 240));
        barra.setBorderPainted(false);
        panel.add(barra);
        
        JLabel num = new JLabel(porcentaje + "%");
        num.setBounds(430, y + 20, 50, 20);
        num.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(num);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new InterfazDashboard().setVisible(true);
    }
}