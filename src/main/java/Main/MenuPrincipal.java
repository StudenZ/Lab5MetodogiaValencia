package Main;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

// TUS IMPORTACIONES (Las mismas de antes)
import hu01_agendar_cita.InterfazAgendar;
import hu03_gestion_cancelacion.InterfazCancelacion;
import hu04_gestion_horarios.InterfazHorarios;
import hu08_agenda_medica.InterfazAgendaDiaria;
import hu11_reportes_admin.InterfazDashboard;
import hu12_historial_paciente.InterfazHistorial;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Sistema Hospitalario - Panel Principal");
        setSize(1000, 700);
        // EL MENÚ PRINCIPAL SÍ DEBE CERRAR TODO EL SISTEMA AL SALIR
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(240, 242, 245));
        add(fondo);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(13, 71, 161));
        header.setPreferredSize(new Dimension(1000, 100));
        header.setBorder(new EmptyBorder(0, 40, 0, 40));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setOpaque(false);
        JLabel titulo = new JLabel("MediSys • Gestión Hospitalaria");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel("Bienvenido al Sistema Centralizado");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(187, 222, 251));
        panelTitulos.add(titulo);
        panelTitulos.add(subtitulo);
        header.add(panelTitulos, BorderLayout.WEST);

        JButton btnSalir = new JButton("Cerrar Sistema");
        btnSalir.setBackground(new Color(21, 101, 192));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        btnSalir.setPreferredSize(new Dimension(130, 40));
        btnSalir.addActionListener(e -> System.exit(0));
        
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 30));
        panelBtn.setOpaque(false);
        panelBtn.add(btnSalir);
        header.add(panelBtn, BorderLayout.EAST);

        fondo.add(header, BorderLayout.NORTH);

        // --- GRID DE MÓDULOS ---
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        gridPanel.setBackground(new Color(240, 242, 245));
        gridPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        

        // 1. HU-01
        gridPanel.add(crearTarjeta("📅", "Agendar Cita", "HU-01: Paciente", new Color(33, 150, 243), e -> {
            InterfazAgendar v = new InterfazAgendar();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        // 2. HU-03
        gridPanel.add(crearTarjeta("❌", "Cancelar Cita", "HU-03: Gestión", new Color(244, 67, 54), e -> {
            InterfazCancelacion v = new InterfazCancelacion();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        // 3. HU-04
        gridPanel.add(crearTarjeta("⏰", "Horarios Médicos", "HU-04: Admin", new Color(103, 58, 183), e -> {
            InterfazHorarios v = new InterfazHorarios();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        // 4. HU-08
        gridPanel.add(crearTarjeta("👨‍⚕️", "Agenda del Doctor", "HU-08: Médico", new Color(255, 152, 0), e -> {
            InterfazAgendaDiaria v = new InterfazAgendaDiaria();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        // 5. HU-11
        gridPanel.add(crearTarjeta("📊", "Reportes Admin", "HU-11: Estadísticas", new Color(0, 150, 136), e -> {
            InterfazDashboard v = new InterfazDashboard();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        // 6. HU-12
        gridPanel.add(crearTarjeta("📂", "Historial Clínico", "HU-12: Archivo", new Color(96, 125, 139), e -> {
            InterfazHistorial v = new InterfazHistorial();
            v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FORZAMOS QUE SOLO SE CIERRE ELLA
            v.setVisible(true);
        }));

        fondo.add(gridPanel, BorderLayout.CENTER);
    }

    private JButton crearTarjeta(String icono, String titulo, String subtitulo, Color colorBorde, ActionListener accion) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, colorBorde));

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcono.setPreferredSize(new Dimension(80, 0));
        
        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setOpaque(false);
        panelTexto.setBorder(new EmptyBorder(10, 0, 10, 10));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(66, 66, 66));
        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(Color.GRAY);
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSub);

        btn.add(lblIcono, BorderLayout.WEST);
        btn.add(panelTexto, BorderLayout.CENTER);
        btn.addActionListener(accion);
        return btn;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new MenuPrincipal().setVisible(true);
    }
}