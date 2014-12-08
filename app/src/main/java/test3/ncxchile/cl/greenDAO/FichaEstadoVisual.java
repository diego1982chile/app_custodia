package test3.ncxchile.cl.greenDAO;

import java.io.Serializable;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FICHA_ESTADO_VISUAL.
 */
public class FichaEstadoVisual implements Serializable {

    private Long id;
    private Boolean valor;
    private String observacion;
    private long idVehiculo;
    private long idEstadoVisual;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient FichaEstadoVisualDao myDao;

    private Vehiculo vehiculo;
    private Long vehiculo__resolvedKey;

    private EstadoVisual estadoVisual;
    private Long estadoVisual__resolvedKey;


    public FichaEstadoVisual() {
    }

    public FichaEstadoVisual(Long id) {
        this.id = id;
    }

    public FichaEstadoVisual(Long id, Boolean valor, String observacion, long idVehiculo, long idEstadoVisual) {
        this.id = id;
        this.valor = valor;
        this.observacion = observacion;
        this.idVehiculo = idVehiculo;
        this.idEstadoVisual = idEstadoVisual;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFichaEstadoVisualDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getValor() {
        return valor;
    }

    public void setValor(Boolean valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public long getIdEstadoVisual() {
        return idEstadoVisual;
    }

    public void setIdEstadoVisual(long idEstadoVisual) {
        this.idEstadoVisual = idEstadoVisual;
    }

    /** To-one relationship, resolved on first access. */
    public Vehiculo getVehiculo() {
        long __key = this.idVehiculo;
        if (vehiculo__resolvedKey == null || !vehiculo__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            VehiculoDao targetDao = daoSession.getVehiculoDao();
            Vehiculo vehiculoNew = targetDao.load(__key);
            synchronized (this) {
                vehiculo = vehiculoNew;
            	vehiculo__resolvedKey = __key;
            }
        }
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new DaoException("To-one property 'idVehiculo' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.vehiculo = vehiculo;
            idVehiculo = vehiculo.getId();
            vehiculo__resolvedKey = idVehiculo;
        }
    }

    /** To-one relationship, resolved on first access. */
    public EstadoVisual getEstadoVisual() {
        long __key = this.idEstadoVisual;
        if (estadoVisual__resolvedKey == null || !estadoVisual__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EstadoVisualDao targetDao = daoSession.getEstadoVisualDao();
            EstadoVisual estadoVisualNew = targetDao.load(__key);
            synchronized (this) {
                estadoVisual = estadoVisualNew;
            	estadoVisual__resolvedKey = __key;
            }
        }
        return estadoVisual;
    }

    public void setEstadoVisual(EstadoVisual estadoVisual) {
        if (estadoVisual == null) {
            throw new DaoException("To-one property 'idEstadoVisual' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.estadoVisual = estadoVisual;
            idEstadoVisual = estadoVisual.getId();
            estadoVisual__resolvedKey = idEstadoVisual;
        }
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
