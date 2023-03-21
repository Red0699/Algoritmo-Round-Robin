import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class ResultadosWindow extends JFrame implements ActionListener {

    private ArrayList<Proceso> listaResultados;
    private int numProceso;
    private int rafaga;
    private int residuo;
    private int quantum;
    private int tiempoServicio;
    private int cont;

    public ResultadosWindow(ArrayList<Proceso> listaResultados, int quantum) {

        this.listaResultados = listaResultados;
        this.quantum = quantum;
        // Configuración de la ventana
        setSize(800, 600);
        setTitle("Simulación Round Robin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración del panel del título
        etiquetaTitulo = new JLabel("Tabla de Procesos");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo = new JPanel();
        panelTitulo.add(etiquetaTitulo);

        // Configuración de la primera tabla
        String[] columnas = {"#", "Tiempo de llegada", "Tiempo de rafaga", "Quantum", "Residuo", "Estado"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(320);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(320);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);

        JLabel etiquetaTabla2 = new JLabel("Tabla 2");
        etiquetaTabla2.setFont(new Font("Arial", Font.BOLD, 16));
        // Configuración de la segunda tabla
        String[] columnasDos = {"#", "Rafafa", "Quantum", "Tiempo Final"};
        modeloTablaDos = new DefaultTableModel(null, columnasDos);
        tabla2 = new JTable(modeloTablaDos);
        tabla2.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla2.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(3).setPreferredWidth(120);

        // Configuración del JScrollPane y panelTabla
        scrollPane = new JScrollPane(tabla);
        scrollPane2 = new JScrollPane(tabla2);
        panelTabla = new JPanel();
        panelTabla.setLayout(null);
        scrollPane.setBounds(0, 0, 800, 300);
        scrollPane2.setBounds(0, 320, 800, 200);
        panelTabla.add(scrollPane);
        panelTabla.add(scrollPane2);

        // Configuración del panel de botones
        botonVerGantt = new JButton("Ver Gantt");
        botonVerGantt.addActionListener(this);
        botonInicio = new JButton("Volver");
        botonInicio.addActionListener(this);
        botonSimular = new JButton("Iniciar");
        botonSimular.addActionListener(this);
        panelBotones = new JPanel();
        panelBotones.add(botonVerGantt);
        panelBotones.add(botonInicio);
        panelBotones.add(botonSimular);

        // Agregamos los paneles a la ventana
        add(panelTitulo, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Llenamos la tabla con los resultados
        llenarTabla(modeloTabla);
        tabla2.setModel(modeloTablaDos);
        setVisible(true);
    }

    // Método para llenar la tabla con los valores ingresados por el usuario
    private void llenarTabla(DefaultTableModel model) {
        for (Proceso resultado : listaResultados) {
            cont++;
            Object[] row = new Object[6];
            row[0] = resultado.getNumProceso();
            row[1] = resultado.getTiempoLlegada();
            row[2] = resultado.getTiempoRafaga();
            row[3] = quantum;
            row[4] = resultado.getTiempoRafaga();
            row[5] = "Listo";
            model.addRow(row);
        }
    }

    //Metodo para correr la simulación
    private class Inicio implements Runnable {

        @Override
        public void run() {
            int estado = 1; //Estado de while que indica si se puede seguir o no
            int i = 0; // contador de while

            while (estado != 0) {
                while (i < cont) { //Recorrer las filas
                    Cargar(i);
                    if (residuo != 0 && residuo > quantum) { //Ejecutando Procesos
                        for (int c = 1; c <= quantum; c++) {
                            tabla.setValueAt("Procesando", i, 5);
                            residuo--;
                            tabla.setValueAt(String.valueOf(residuo), i, 4);
                            tiempoServicio++;
                            Dormir();
                        }
                        tabla.setValueAt("Espera", i, 5);
                        if (residuo == 0) {
                            tabla.setValueAt("Terminado", i, 5);
                            Resultado(i, modeloTablaDos);
                            Borrar(i);
                        }
                    } else {
                        if (residuo > 0 && quantum != 0) {
                            while (residuo > 0) {
                                tabla.setValueAt("Procesando", i, 5);
                                residuo--;
                                tabla.setValueAt(String.valueOf(residuo), i, 4);
                                tiempoServicio++;
                                Dormir();
                            }
                            tabla.setValueAt("Espera", i, 5);
                            if (residuo == 0 && quantum != 0) {
                                tabla.setValueAt("Terminado", i, 5);
                                Resultado(i, modeloTablaDos);
                                Borrar(i);
                            }
                        } else {
                            if (residuo == 0 && quantum != 0) {
                                tabla.setValueAt("Terminado", i, 5);
                                Resultado(i, modeloTablaDos);
                                Borrar(i);
                            }
                        }
                    }
                    i++;
                }
                i = 0;

            }
        }
    }

    //Carga los valores de la primera Tabla
    public void Cargar(int i) {
        numProceso = Integer.parseInt(tabla.getValueAt(i, 0).toString());
        rafaga = Integer.parseInt(tabla.getValueAt(i, 2).toString());
        quantum = Integer.parseInt(tabla.getValueAt(i, 3).toString());
        residuo = Integer.parseInt(tabla.getValueAt(i, 4).toString());
    }

    public void Dormir() {
        try {
            Thread.sleep(700); //Dormir sistema
        } catch (InterruptedException ex) {
            Logger.getLogger(Procesar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Elimina los registros de la primera tabla
    public void Borrar(int c) {
        tabla.setValueAt(0, c, 0);
        tabla.setValueAt("0", c, 1);
        tabla.setValueAt("0", c, 2);
        tabla.setValueAt("0", c, 3);
        tabla.setValueAt("0", c, 4);
        tabla.setValueAt("******", c, 5);
    }

    //Carga los valores finales de los procesos a la segunda tabla
    public void Resultado(int c, DefaultTableModel modelo2) {
        modelo2 = (DefaultTableModel) tabla2.getModel();

        Object[] row = new Object[5];
        row[0] = c + 1;
        row[1] = rafaga;
        row[2] = quantum;
        row[3] = tiempoServicio + " Segundos";
        row[4] = "Terminado";
        modelo2.addRow(row);
        tabla2.setModel(modelo2);

    }

    // Acciones de los botones
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonVerGantt) {
            //new VentanaGantt(listaResultados);
            setVisible(false);
        } else if (e.getSource() == botonInicio) {
            new Inicio();
            setVisible(true);
        } else if (e.getSource() == botonSimular) {
            new Thread(new ResultadosWindow.Inicio()).start();
        }
    }

    //Variables del Swing GUI
    private JButton botonVerGantt, botonInicio, botonSimular;
    private JLabel etiquetaTitulo;
    private JPanel panelTitulo, panelTabla, panelBotones;
    private JTable tabla, tabla2;
    private JScrollPane scrollPane, scrollPane2;
    private DefaultTableModel modeloTabla, modeloTablaDos;
}
