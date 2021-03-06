package test3.ncxchile.cl.greenDAO;

import java.util.List;
import test3.ncxchile.cl.greenDAO.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table Vehiculo.
 */
public class Vehiculo {

    private Long id;
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
    private Integer servicio;
    private String vin;
    private Boolean origenVehiculo;
    private Boolean puedeRodar;
    private Boolean clonado;
    private long fichaEstadovisualID;
    private long parqueaderoSummaryID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient VehiculoDao myDao;

    private ParqueaderoSummary parqueaderoSummary;
    private Long parqueaderoSummary__resolvedKey;

    private List<FichaEstadoVisual> fichaEstadoVisualList;

    public Vehiculo() {
    }

    public Vehiculo(Long id) {
        this.id = id;
    }

    public Vehiculo(Long id, String marca, String modelo, Integer anio, String color, String matricula, Boolean modificado, String caracteristicas, String numeroChasis, String numeroMotor, String tamano, Long kilometraje, String carpetaVehiculo, Integer servicio, String vin, Boolean origenVehiculo, Boolean puedeRodar, Boolean clonado, long fichaEstadovisualID, long parqueaderoSummaryID) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.matricula = matricula;
        this.modificado = modificado;
        this.caracteristicas = caracteristicas;
        this.numeroChasis = numeroChasis;
        this.numeroMotor = numeroMotor;
        this.tamano = tamano;
        this.kilometraje = kilometraje;
        this.carpetaVehiculo = carpetaVehiculo;
        this.servicio = servicio;
        this.vin = vin;
        this.origenVehiculo = origenVehiculo;
        this.puedeRodar = puedeRodar;
        this.clonado = clonado;
        this.fichaEstadovisualID = fichaEstadovisualID;
        this.parqueaderoSummaryID = parqueaderoSummaryID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVehiculoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(Integer servicio) {
        this.servicio = servicio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public Boolean getClonado() {
        return clonado;
    }

    public void setClonado(Boolean clonado) {
        this.clonado = clonado;
    }

    public long getFichaEstadovisualID() {
        return fichaEstadovisualID;
    }

    public void setFichaEstadovisualID(long fichaEstadovisualID) {
        this.fichaEstadovisualID = fichaEstadovisualID;
    }

    public long getParqueaderoSummaryID() {
        return parqueaderoSummaryID;
    }

    public void setParqueaderoSummaryID(long parqueaderoSummaryID) {
        this.parqueaderoSummaryID = parqueaderoSummaryID;
    }

    /** To-one relationship, resolved on first access. */
    public ParqueaderoSummary getParqueaderoSummary() {
        long __key = this.parqueaderoSummaryID;
        if (parqueaderoSummary__resolvedKey == null || !parqueaderoSummary__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParqueaderoSummaryDao targetDao = daoSession.getParqueaderoSummaryDao();
            ParqueaderoSummary parqueaderoSummaryNew = targetDao.load(__key);
            synchronized (this) {
                parqueaderoSummary = parqueaderoSummaryNew;
            	parqueaderoSummary__resolvedKey = __key;
            }
        }
        return parqueaderoSummary;
    }

    public void setParqueaderoSummary(ParqueaderoSummary parqueaderoSummary) {
        if (parqueaderoSummary == null) {
            throw new DaoException("To-one property 'parqueaderoSummaryID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.parqueaderoSummary = parqueaderoSummary;
            parqueaderoSummaryID = parqueaderoSummary.getId();
            parqueaderoSummary__resolvedKey = parqueaderoSummaryID;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<FichaEstadoVisual> getFichaEstadoVisualList() {
        if (fichaEstadoVisualList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FichaEstadoVisualDao targetDao = daoSession.getFichaEstadoVisualDao();
            List<FichaEstadoVisual> fichaEstadoVisualListNew = targetDao._queryVehiculo_FichaEstadoVisualList(id);
            synchronized (this) {
                if(fichaEstadoVisualList == null) {
                    fichaEstadoVisualList = fichaEstadoVisualListNew;
                }
            }
        }
        return fichaEstadoVisualList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFichaEstadoVisualList() {
        fichaEstadoVisualList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
