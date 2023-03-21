/*

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
    private ArrayList<Proceso> listaProcesos;
    private int quantum;

    public RoundRobin(ArrayList<Proceso> listaProcesos, int quantum) {
        this.listaProcesos = listaProcesos;
        this.quantum = quantum;
    }

    public ArrayList<ResultadoProceso> simularProcesos() {
        ArrayList<ResultadoProceso> listaResultados = new ArrayList<>();
        Queue<Proceso> colaProcesos = new LinkedList<>();
        int tiempo = 0;

        while (!listaProcesos.isEmpty() || !colaProcesos.isEmpty()) {
            // Agregar nuevos procesos a la cola de espera
            for (Proceso proceso : listaProcesos) {
                if (proceso.getTiempoLlegada() == tiempo) {
                    colaProcesos.add(proceso);
                }
            }

            // Ejecutar el primer proceso en la cola
            if (!colaProcesos.isEmpty()) {
                Proceso procesoActual = colaProcesos.peek();

                // Verificar si el proceso ha terminado
                if (procesoActual.getTiempoRestante() <= quantum) {
                    tiempo += procesoActual.getTiempoRestante();
                    procesoActual.setTiempoRestante(0);
                    listaResultados.add(new ResultadoProceso(tiempo, procesoActual.getTiempoServicio(), tiempo - procesoActual.getTiempoLlegada() - procesoActual.getTiempoServicio(), procesoActual));
                    colaProcesos.poll();
                } else {
                    tiempo += quantum;
                    procesoActual.setTiempoRestante(procesoActual.getTiempoRestante() - quantum);
                    colaProcesos.poll();
                    colaProcesos.add(procesoActual);
                }
            } else {
                tiempo++;
            }
        }
        return listaResultados;
    }
}
*/

