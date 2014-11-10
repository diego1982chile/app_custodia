package test3.ncxchile.cl.models;

import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by BOBO on 23-07-2014.
 */
public class Acta {

    private long id;
    private VehiculoData vehiculoData;
    private String observacion;
    private String causaRetiro;
    private Direccion direccion;
    private Autoridad autoridad;
    private boolean existImage;
    private boolean existVideo;
    private Persona gruero;
    private Date fechaCreacion;
    private Date fechaFirma;
    private int idSolicitud;
    private Integer idOt;
    private Integer idGrua;
    private boolean fiscalia;
    private String nue;
    private String ruc;
    private String parte;
    private String unidadPolicial;
    private Date fechaParte;
    private Integer servicio;
    private Boolean gruaExterna;
    private String observacionImgenes;
    private Institucion tribunal;
    private String nombreExterno;
    private Integer numeroFactura;
    private Integer montoFactura;
    private String numeroPatente;
    private Boolean cargaInicial;
    private String actaIncautacion;
    private String oficioRemisor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehiculoData getVehiculoData() {
        return vehiculoData;
    }

    public void setVehiculoData(VehiculoData vehiculoDataData) {
        this.vehiculoData = vehiculoDataData;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCausaRetiro() {
        return causaRetiro;
    }

    public void setCausaRetiro(String causaRetiro) {
        this.causaRetiro = causaRetiro;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Autoridad getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(Autoridad autoridad) {
        this.autoridad = autoridad;
    }

    public boolean isExistImage() {
        return existImage;
    }

    public void setExistImage(boolean existImage) {
        this.existImage = existImage;
    }

    public boolean isExistVideo() {
        return existVideo;
    }

    public void setExistVideo(boolean existVideo) {
        this.existVideo = existVideo;
    }

    public Persona getGruero() {
        return gruero;
    }

    public void setGruero(Persona gruero) {
        this.gruero = gruero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdOt() {
        return idOt;
    }

    public void setIdOt(Integer idOt) {
        this.idOt = idOt;
    }

    public Integer getIdGrua() {
        return idGrua;
    }

    public void setIdGrua(Integer idGrua) {
        this.idGrua = idGrua;
    }

    public boolean isFiscalia() {
        return fiscalia;
    }

    public void setFiscalia(boolean fiscalia) {
        this.fiscalia = fiscalia;
    }

    public String getNue() {
        return nue;
    }

    public void setNue(String nue) {
        this.nue = nue;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getUnidadPolicial() {
        return unidadPolicial;
    }

    public void setUnidadPolicial(String unidadPolicial) {
        this.unidadPolicial = unidadPolicial;
    }

    public Date getFechaParte() {
        return fechaParte;
    }

    public void setFechaParte(Date fechaParte) {
        this.fechaParte = fechaParte;
    }

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    public Boolean getGruaExterna() {
        return gruaExterna;
    }

    public void setGruaExterna(Boolean gruaExterna) {
        this.gruaExterna = gruaExterna;
    }

    public String getObservacionImgenes() {
        return observacionImgenes;
    }

    public void setObservacionImgenes(String observacionImgenes) {
        this.observacionImgenes = observacionImgenes;
    }

    public Institucion getTribunal() {
        return tribunal;
    }

    public void setTribunal(Institucion tribunal) {
        this.tribunal = tribunal;
    }

    public String getNombreExterno() {
        return nombreExterno;
    }

    public void setNombreExterno(String nombreExterno) {
        this.nombreExterno = nombreExterno;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Integer getMontoFactura() {
        return montoFactura;
    }

    public void setMontoFactura(Integer montoFactura) {
        this.montoFactura = montoFactura;
    }

    public String getNumeroPatente() {
        return numeroPatente;
    }

    public void setNumeroPatente(String numeroPatente) {
        this.numeroPatente = numeroPatente;
    }

    public Boolean getCargaInicial() {
        return cargaInicial;
    }

    public void setCargaInicial(Boolean cargaInicial) {
        this.cargaInicial = cargaInicial;
    }

    public String getActaIncautacion() {
        return actaIncautacion;
    }

    public void setActaIncautacion(String actaIncautacion) {
        this.actaIncautacion = actaIncautacion;
    }

    public String getOficioRemisor() {
        return oficioRemisor;
    }

    public void setOficioRemisor(String oficioRemisor) {
        this.oficioRemisor = oficioRemisor;
    }
}
