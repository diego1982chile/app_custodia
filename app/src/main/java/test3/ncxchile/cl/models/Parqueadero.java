package test3.ncxchile.cl.models;

/**
 * Created by BOBO on 23-07-2014.
 */
public class Parqueadero {

    private Integer numero;
    private String sector;
    private Integer servicio;
    private String recinto;
    private Boolean disponible;
    private Agrupador agrupador;
    private Integer numeroEnAgrupador;
    private ParqueaderoSummary agrupadoPor;
    private ParqueaderoSummary fusionadoCon;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Agrupador getAgrupador() {
        return agrupador;
    }

    public void setAgrupador(Agrupador agrupador) {
        this.agrupador = agrupador;
    }

    public Integer getNumeroEnAgrupador() {
        return numeroEnAgrupador;
    }

    public void setNumeroEnAgrupador(Integer numeroEnAgrupador) {
        this.numeroEnAgrupador = numeroEnAgrupador;
    }

    public ParqueaderoSummary getAgrupadoPor() {
        return agrupadoPor;
    }

    public void setAgrupadoPor(ParqueaderoSummary agrupadoPor) {
        this.agrupadoPor = agrupadoPor;
    }

    public ParqueaderoSummary getFusionadoCon() {
        return fusionadoCon;
    }

    public void setFusionadoCon(ParqueaderoSummary fusionadoCon) {
        this.fusionadoCon = fusionadoCon;
    }
}
