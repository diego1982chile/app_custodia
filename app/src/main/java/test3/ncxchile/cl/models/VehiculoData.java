package test3.ncxchile.cl.models;

import java.util.List;

/**
 * Created by BOBO on 23-07-2014.
 */
public class VehiculoData {
    private Vehiculo vehiculo;
    private List<String> especias;
    private Cliente conductor;
    private Cliente propietario;
    private Parqueadero parqueadero;

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<String> getEspecias() {
        return especias;
    }

    public void setEspecias(List<String> especias) {
        this.especias = especias;
    }

    public Cliente getConductor() {
        return conductor;
    }

    public void setConductor(Cliente conductor) {
        this.conductor = conductor;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }
}
