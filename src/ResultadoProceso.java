public class ResultadoProceso {
    private int numeroProceso;
    private int tiempoFinal;
    private int tiempoServicio;
    private int tiempoEspera;

    private String nombreProceso;

    private Proceso proceso;

    public ResultadoProceso(int numeroProceso, String nombreProceso, int tiempoFinal, int tiempoServicio, int tiempoEspera) {
        this.numeroProceso = numeroProceso;
        this.nombreProceso = nombreProceso;
        this.tiempoFinal = tiempoFinal;
        this.tiempoServicio = tiempoServicio;
        this.tiempoEspera = tiempoEspera;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public int getNumeroProceso() {
        return numeroProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public int getTiempoFinal() {
        return tiempoFinal;
    }

    public int getTiempoServicio() {
        return tiempoServicio;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }
}
