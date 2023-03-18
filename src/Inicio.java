import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Inicio extends JFrame {
    private JLabel quantumLabel;
    private JLabel rafagaLabel;
    private JTextField quantumText;
    private JTextField rafagaText;
    private JButton agregarButton;
    private JButton simularButton;
    private JTable tablaProcesos;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<Proceso> listaProcesos;

    public Inicio() {
        super("Round Robin");

        listaProcesos = new ArrayList<>();
        quantumLabel = new JLabel("Quantum:");
        rafagaLabel = new JLabel("Tiempo en ráfaga:");
        quantumText = new JTextField(10);
        rafagaText = new JTextField(10);
        agregarButton = new JButton("Agregar proceso");
        simularButton = new JButton("Simular");
        model = new DefaultTableModel();
        tablaProcesos = new JTable(model);
        model.addColumn("Proceso");
        model.addColumn("Quantum");
        model.addColumn("Tiempo en ráfaga");
        scrollPane = new JScrollPane(tablaProcesos);
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel panelInferior = new JPanel();

        panelSuperior.add(quantumLabel);
        panelSuperior.add(quantumText);
        panelSuperior.add(rafagaLabel);
        panelSuperior.add(rafagaText);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        panelInferior.add(agregarButton);
        panelInferior.add(simularButton);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProceso();
            }
        });

        simularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simular();
            }
        });

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void agregarProceso() {
        String quantum = quantumText.getText();
        String rafaga = rafagaText.getText();

        if (quantum.isEmpty() || rafaga.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos");
        } else {
            int numeroProceso = model.getRowCount() + 1;
            model.addRow(new String[]{String.valueOf(numeroProceso), quantum, rafaga});
            Proceso proceso = new Proceso(numeroProceso, Integer.parseInt(quantum), Integer.parseInt(rafaga));
            listaProcesos.add(proceso);
            quantumText.setText("");
            rafagaText.setText("");
        }
    }

    private void simular() {
        String quantum = quantumText.getText();
        if (listaProcesos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese al menos un proceso");
        } else {
            RoundRobin rr = new RoundRobin(listaProcesos, Integer.parseInt(quantum));
            ResultadosWindow ventana2 = new ResultadosWindow(rr.simularProcesos());
            ventana2.setVisible(true);
            dispose();
        }
    }
}
