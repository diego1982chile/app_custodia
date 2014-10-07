package test3.ncxchile.cl.models;

import java.util.List;

/**
 * Created by BOBO on 23-07-2014.
 */
public class Vehiculo {

    private String marca;
    private String modelo;
    private Integer anio;
    private String color;
    private String matricula;
    private Boolean modificado;
    private String caracteristicas;
    private String numeroChasis;
    private String numeroMotor;
    private String tamano;
    private Long kilometraje;
    private String carpetaVehiculo;
    private Integer id;
    private int servicio;
    private String vin;
    private List<FichaEstadoVisual> fichaEstadoVisual;
    private Boolean origenVehiculo;
    private Boolean puedeRodar;
    private ParqueaderoSummary parqueadero;
    private Boolean clonado;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public Long getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Long kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getCarpetaVehiculo() {
        return carpetaVehiculo;
    }

    public void setCarpetaVehiculo(String carpetaVehiculo) {
        this.carpetaVehiculo = carpetaVehiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<FichaEstadoVisual> getFichaEstadoVisual() {
        return fichaEstadoVisual;
    }

    public void setFichaEstadoVisual(List<FichaEstadoVisual> fichaEstadoVisual) {
        this.fichaEstadoVisual = fichaEstadoVisual;
    }

    public Boolean getOrigenVehiculo() {
        return origenVehiculo;
    }

    public void setOrigenVehiculo(Boolean origenVehiculo) {
        this.origenVehiculo = origenVehiculo;
    }

    public Boolean getPuedeRodar() {
        return puedeRodar;
    }

    public void setPuedeRodar(Boolean puedeRodar) {
        this.puedeRodar = puedeRodar;
    }

    public ParqueaderoSummary getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(ParqueaderoSummary parqueadero) {
        this.parqueadero = parqueadero;
    }

    public Boolean getClonado() {
        return clonado;
    }

    public void setClonado(Boolean clonado) {
        this.clonado = clonado;
    }
}
