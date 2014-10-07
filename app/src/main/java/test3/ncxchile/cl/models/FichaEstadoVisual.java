package test3.ncxchile.cl.models;

/**
 * Created by BOBO on 23-07-2014.
 */
public class FichaEstadoVisual {

    private int idFichaEstadoVisual;
    private int idFicha;
    private EstadoVisual estadoVisual;
    private boolean valor;
    private String observacion;

    public int getIdFichaEstadoVisual() {
        return idFichaEstadoVisual;
    }

    public void setIdFichaEstadoVisual(int idFichaEstadoVisual) {
        this.idFichaEstadoVisual = idFichaEstadoVisual;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public EstadoVisual getEstadoVisual() {
        return estadoVisual;
    }

    public void setEstadoVisual(EstadoVisual estadoVisual) {
        this.estadoVisual = estadoVisual;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
