public class Proceso {
    private int tiempoRafaga;
    private int tiempoRestante;
    private int tiempoLlegada;
    private int tiempoServicio;
    private int tiempoEspera;
    private int numProceso;

    private int quantum;

    public Proceso(int numProceso, int quantum , int tiempoRafaga, int tiempoLlegada) {
        this.numProceso = numProceso;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga;
        this.tiempoLlegada = tiempoLlegada;
        this.quantum = quantum;
        this.tiempoServicio = 0;
        this.tiempoEspera = 0;
    }

    public int getNumProceso() {
        return numProceso;
    }
    public int getTiempoRafaga() {
        return tiempoRafaga;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }


    public int getTiempoServicio() {
        return tiempoServicio;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }


}
