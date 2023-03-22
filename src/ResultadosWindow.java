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

    private ArrayList<Proceso> listaProcesos;
    private int numProceso;
    private int ejecucion;
    private int residuo;
    private int quantum;
    private int tiempoFinal;
    private int tiempoLlegada;
    private int cont;

    public ResultadosWindow(ArrayList<Proceso> listaProcesos, int quantum) {

        this.listaProcesos = listaProcesos;
        this.quantum = quantum;
        // Configuración de la ventana
        setSize(820, 600);
        setTitle("Simulación Round Robin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuración del panel del título
        etiquetaTitulo = new JLabel("Tabla de Procesos");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo = new JPanel();
        panelTitulo.add(etiquetaTitulo);

        // Configuración de la primera tabla
        String[] columnas = {"#", "Tiempo de llegada", "Tiempo de ejecución", "Quantum", "Residuo", "Estado"};
        modeloTabla = new DefaultTableModel(null, columnas);
        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(320);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(320);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);

        etiquetaTabla2 = new JLabel("Tabla 2");
        etiquetaTabla2.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo2 = new JPanel();
        panelTitulo2.add(etiquetaTabla2);
        // Configuración de la segunda tabla
        String[] columnasDos = {"#","Tiempo Llegada" ,"Tiempo Ejecución", "Quantum", "Tiempo Final", "Tiempo Servicio", "Tiempo Espera"};
        modeloTablaDos = new DefaultTableModel(null, columnasDos);
        tabla2 = new JTable(modeloTablaDos);
        tabla2.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla2.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabla2.getColumnModel().getColumn(6).setPreferredWidth(120);

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
        panelBotones.add(botonInicio);
        panelBotones.add(botonSimular);

        // Agregamos los paneles a la ventana
        add(panelTitulo, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        //add(panelTitulo2, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Llenamos la tabla con los resultados
        llenarTabla(modeloTabla);
        tabla2.setModel(modeloTablaDos);
        setVisible(true);
    }

    // Método para llenar la tabla con los valores ingresados por el usuario
    private void llenarTabla(DefaultTableModel model) {
        for (Proceso resultado : listaProcesos) {
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
    private class Iniciar implements Runnable {
        //long tiempoInicio = System.currentTimeMillis();
        @Override
        public void run() {
            int estado = 1; //Estado de while que indica si se puede seguir o no
            int i = 0; // contador de while

            while (estado != 0) {
                while (i < cont) { //Recorrer las filas
                    Cargar(i);
                    if (residuo != 0 && residuo > quantum && tiempoFinal >= tiempoLlegada) { //Ejecutando Procesos
                        for (int c = 1; c <= quantum; c++) {
                            tabla.setValueAt("Procesando", i, 5);
                            residuo--;
                            tabla.setValueAt(String.valueOf(residuo), i, 4);
                            tiempoFinal++;
                            Dormir();
                        }
                        tabla.setValueAt("Espera", i, 5);
                        if (residuo == 0) {
                            tabla.setValueAt("Terminado", i, 5);
                            Resultado(i, modeloTablaDos);
                            Borrar(i);
                        }
                    } else {
                        if (residuo > 0 && quantum != 0 && tiempoFinal >= tiempoLlegada) {
                            while (residuo > 0) {
                                tabla.setValueAt("Procesando", i, 5);
                                residuo--;
                                tabla.setValueAt(String.valueOf(residuo), i, 4);
                                tiempoFinal++;
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
        ejecucion = Integer.parseInt(tabla.getValueAt(i, 2).toString());
        quantum = Integer.parseInt(tabla.getValueAt(i, 3).toString());
        residuo = Integer.parseInt(tabla.getValueAt(i, 4).toString());
        tiempoLlegada = Integer.parseInt(tabla.getValueAt(i, 1).toString());
    }

    public void Dormir() {
        try {
            Thread.sleep(1000); //Dormir sistema
        } catch (InterruptedException ex) {
            Logger.getLogger(ResultadosWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Elimina los registros de la primera tabla
    public void Borrar(int c) {
        tabla.setValueAt(0, c, 0);
        tabla.setValueAt("0", c, 1);
        tabla.setValueAt("0", c, 2);
        tabla.setValueAt("0", c, 3);
        tabla.setValueAt("0", c, 4);
        tabla.setValueAt("Finalizado", c, 5);
    }

    //Carga los valores finales de los procesos a la segunda tabla
    public void Resultado(int c, DefaultTableModel modelo2) {
        modelo2 = (DefaultTableModel) tabla2.getModel();

        Object[] row = new Object[7];
        row[0] = c + 1;
        row[1] = tiempoLlegada;
        row[2] = ejecucion;
        row[3] = quantum;
        row[4] = tiempoFinal;
        row[5] = tiempoFinal - tiempoLlegada;
        row[6] = (tiempoFinal - tiempoLlegada) - ejecucion;
        modelo2.addRow(row);
        tabla2.setModel(modelo2);
    }

    // Acciones de los botones
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == botonInicio) {
            new Inicio().setVisible(true);
            dispose();
        } else if (e.getSource() == botonSimular) {
            new Thread(new ResultadosWindow.Iniciar()).start();
        }
    }

    //Variables del Swing GUI
    private JButton botonVerGantt, botonInicio, botonSimular;
    private JLabel etiquetaTitulo, etiquetaTabla2;
    private JPanel panelTitulo, panelTabla, panelBotones, panelTitulo2;
    private JTable tabla, tabla2;
    private JScrollPane scrollPane, scrollPane2;
    private DefaultTableModel modeloTabla, modeloTablaDos;
}
