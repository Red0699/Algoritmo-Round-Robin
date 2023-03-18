import java.util.ArrayList;

public class RoundRobin {
    private ArrayList<Proceso> listaProcesos;
    private int quantum;

    public RoundRobin(ArrayList<Proceso> listaProcesos, int quantum) {
        this.listaProcesos = listaProcesos;
        this.quantum = quantum;
    }

    public ArrayList<ResultadoProceso> simularProcesos() {
        ArrayList<ResultadoProceso> listaResultados = new ArrayList<>();
        ArrayList<Proceso> colaProcesos = new ArrayList<>();
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
                Proceso procesoActual = colaProcesos.get(0);

                // Verificar si el proceso ha terminado
                if (procesoActual.getTiempoRestante() <= quantum) {
                    tiempo += procesoActual.getTiempoRestante();
                    procesoActual.setTiempoRestante(0);
                    listaResultados.add(new ResultadoProceso( procesoActual.getNumProceso(), procesoActual.getNombre(), tiempo, procesoActual.getTiempoServicio(), tiempo - procesoActual.getTiempoLlegada() - procesoActual.getTiempoServicio()));
                    colaProcesos.remove(0);
                } else {
                    tiempo += quantum;
                    procesoActual.setTiempoRestante(procesoActual.getTiempoRestante() - quantum);
                    colaProcesos.add(colaProcesos.remove(0));
                }
            } else {
                tiempo++;
            }
        }

        return listaResultados;
    }
}
