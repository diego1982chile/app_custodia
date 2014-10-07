package test3.ncxchile.cl.vectors;

import test3.ncxchile.cl.models.Autoridad;

/**
 * Created by Agustin on 24-09-2014.
 */
public class ActaVector {

    private Autoridad autoridad_solicitante;
    private Autoridad autoridad_recibe;
    private String dirección;
    private String comuna;
    private String entre_calles;
    private String motivo;
    private String otras_referencia;
    private String rut_gruero;
    private String nombre_gruero;
    private String patente_gruero;

    public Autoridad getAutoridad_solicitante() {
        return autoridad_solicitante;
    }

    public void setAutoridad_solicitante(Autoridad autoridad_solicitante) {
        this.autoridad_solicitante = autoridad_solicitante;
    }

    public Autoridad getAutoridad_recibe() {
        return autoridad_recibe;
    }

    public void setAutoridad_recibe(Autoridad autoridad_recibe) {
        this.autoridad_recibe = autoridad_recibe;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getEntre_calles() {
        return entre_calles;
    }

    public void setEntre_calles(String entre_calles) {
        this.entre_calles = entre_calles;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOtras_referencia() {
        return otras_referencia;
    }

    public void setOtras_referencia(String otras_referencia) {
        this.otras_referencia = otras_referencia;
    }

    public String getRut_gruero() {
        return rut_gruero;
    }

    public void setRut_gruero(String rut_gruero) {
        this.rut_gruero = rut_gruero;
    }

    public String getNombre_gruero() {
        return nombre_gruero;
    }

    public void setNombre_gruero(String nombre_gruero) {
        this.nombre_gruero = nombre_gruero;
    }

    public String getPatente_gruero() {
        return patente_gruero;
    }

    public void setPatente_gruero(String patente_gruero) {
        this.patente_gruero = patente_gruero;
    }
}
