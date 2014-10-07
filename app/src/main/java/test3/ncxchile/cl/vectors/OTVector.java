package test3.ncxchile.cl.vectors;

import test3.ncxchile.cl.models.Autoridad;

/**
 * Created by Agustin on 24-09-2014.
 */
public class OTVector {

    private int id;
    private String numero_ot;
    private Autoridad autoridad_solicitante;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero_ot() {
        return numero_ot;
    }

    public void setNumero_ot(String numero_ot) {
        this.numero_ot = numero_ot;
    }

    public Autoridad getAutoridad_solicitante() {
        return autoridad_solicitante;
    }

    public void setAutoridad_solicitante(Autoridad autoridad_solicitante) {
        this.autoridad_solicitante = autoridad_solicitante;
    }
}
