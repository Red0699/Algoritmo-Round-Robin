import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ResultadosWindow extends JFrame implements ActionListener {

    private JButton botonVerGantt, botonInicio;
    private JLabel etiquetaTitulo;
    private JPanel panelTitulo, panelTabla, panelBotones;
    private JTable tabla;
    private JScrollPane scrollPane;
    private DefaultTableModel modeloTabla;
    private ArrayList<ResultadoProceso> listaResultados;

    public ResultadosWindow(ArrayList<ResultadoProceso> listaResultados) {

        this.listaResultados = listaResultados;

        // Configuración de la ventana
        setSize(800, 600);
        setTitle("Simulación Round Robin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración del panel del título
        etiquetaTitulo = new JLabel("Resultados de la simulación");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo = new JPanel();
        panelTitulo.add(etiquetaTitulo);

        // Configuración de la tabla
        String[] columnas = {"Proceso", "Tiempo de llegada", "Tiempo de rafaga", "Tiempo final", "Tiempo de servicio", "Tiempo de espera"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);
        scrollPane = new JScrollPane(tabla);
        panelTabla = new JPanel();
        panelTabla.add(scrollPane);

        // Configuración del panel de botones
        botonVerGantt = new JButton("Ver Gantt");
        botonVerGantt.addActionListener(this);
        botonInicio = new JButton("Inicio");
        botonInicio.addActionListener(this);
        panelBotones = new JPanel();
        panelBotones.add(botonVerGantt);
        panelBotones.add(botonInicio);

        // Agregamos los paneles a la ventana
        add(panelTitulo, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Llenamos la tabla con los resultados
        llenarTabla(modeloTabla);

        setVisible(true);
    }

    // Método para llenar la tabla con los resultados
    private void llenarTabla(DefaultTableModel model) {
        for (ResultadoProceso resultado : listaResultados) {
            Object[] row = new Object[5];
            row[0] = resultado.getProceso().getNumProceso();
            row[1] = resultado.getProceso().getTiempoLlegada();
            row[2] = resultado.getTiempoFinal();
            row[3] = resultado.getTiempoServicio();
            row[4] = resultado.getTiempoEspera();
            model.addRow(row);
        }
    }

    // Acciones de los botones
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVerGantt) {
            //new VentanaGantt(listaResultados);
            setVisible(false);
        } else if (e.getSource() == botonInicio) {
            new Inicio();
            setVisible(false);
        }
    }
}
