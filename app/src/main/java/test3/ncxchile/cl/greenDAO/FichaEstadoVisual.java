package test3.ncxchile.cl.greenDAO;

import test3.ncxchile.cl.greenDAO.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FichaEstadoVisual.
 */
public class FichaEstadoVisual {

    private Long id;
    private Integer idFichaEstadoVisual;
    private Integer idFicha;
    private Boolean valor;
    private String observacion;
    private long estadoVisualID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient FichaEstadoVisualDao myDao;

    private EstadoVisual estadoVisual;
    private Long estadoVisual__resolvedKey;


    public FichaEstadoVisual() {
    }

    public FichaEstadoVisual(Long id) {
        this.id = id;
    }

    public FichaEstadoVisual(Long id, Integer idFichaEstadoVisual, Integer idFicha, Boolean valor, String observacion, long estadoVisualID) {
        this.id = id;
        this.idFichaEstadoVisual = idFichaEstadoVisual;
        this.idFicha = idFicha;
        this.valor = valor;
        this.observacion = observacion;
        this.estadoVisualID = estadoVisualID;
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

    public Integer getIdFichaEstadoVisual() {
        return idFichaEstadoVisual;
    }

    public void setIdFichaEstadoVisual(Integer idFichaEstadoVisual) {
        this.idFichaEstadoVisual = idFichaEstadoVisual;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
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

    public long getEstadoVisualID() {
        return estadoVisualID;
    }

    public void setEstadoVisualID(long estadoVisualID) {
        this.estadoVisualID = estadoVisualID;
    }

    /** To-one relationship, resolved on first access. */
    public EstadoVisual getEstadoVisual() {
        long __key = this.estadoVisualID;
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
            throw new DaoException("To-one property 'estadoVisualID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.estadoVisual = estadoVisual;
            estadoVisualID = estadoVisual.getId();
            estadoVisual__resolvedKey = estadoVisualID;
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
