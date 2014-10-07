package test3.ncxchile.cl.models;

/**
 * Created by BOBO on 23-07-2014.
 */
public class ParqueaderoSummary {

    private String nave;
    private int numero;
    private int numeroEnAgrupador;
    private int agrupador;
    private String recinto;
    private boolean tribunales;
    private String estado;
    private boolean disponible;
    private int servicio;
    private String tamano;

    public String getNave() {
        return nave;
    }

    public void setNave(String nave) {
        this.nave = nave;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumeroEnAgrupador() {
        return numeroEnAgrupador;
    }

    public void setNumeroEnAgrupador(int numeroEnAgrupador) {
        this.numeroEnAgrupador = numeroEnAgrupador;
    }

    public int getAgrupador() {
        return agrupador;
    }

    public void setAgrupador(int agrupador) {
        this.agrupador = agrupador;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }

    public boolean isTribunales() {
        return tribunales;
    }

    public void setTribunales(boolean tribunales) {
        this.tribunales = tribunales;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }
}
