public class Proceso {
    private String nombre;
    private int tiempoRafaga;
    private int tiempoRestante;
    private int tiempoLlegada;
    private int tiempoFinalizacion;
    private int tiempoServicio;
    private int tiempoEspera;

    private int numProceso;

    public Proceso(int numProceso, int tiempoRafaga, int tiempoLlegada) {
        this.numProceso = numProceso;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoRestante = tiempoRafaga;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoFinalizacion = -1;
        this.tiempoServicio = 0;
        this.tiempoEspera = 0;
    }

    public int getNumProceso() {
        return numProceso;
    }

    public void setNumProceso(int numProceso) {
        this.numProceso = numProceso;
    }

    public String getNombre() {
        return nombre;
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

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public int getTiempoServicio() {
        return tiempoServicio;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public void setTiempoFinalizacion(int tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public void calcularTiempos() {
        this.tiempoServicio = this.tiempoRafaga;
        this.tiempoEspera = this.tiempoFinalizacion - this.tiempoLlegada - this.tiempoRafaga;
    }
}
