package co.woofy.upload;

import co.woofy.upload.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ACCION.
 */
public class Accion {

    private Long id;
    /** Not-null value. */
    private java.util.Date timeStamp;
    private Float longitud;
    private Float latitud;
    private Boolean sincronizada;
    private long idTarea;
    private Long idActa;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AccionDao myDao;

    private Tarea tarea;
    private Long tarea__resolvedKey;

    private Acta acta;
    private Long acta__resolvedKey;


    public Accion() {
    }

    public Accion(Long id) {
        this.id = id;
    }

    public Accion(Long id, java.util.Date timeStamp, Float longitud, Float latitud, Boolean sincronizada, long idTarea, Long idActa) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.longitud = longitud;
        this.latitud = latitud;
        this.sincronizada = sincronizada;
        this.idTarea = idTarea;
        this.idActa = idActa;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAccionDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public java.util.Date getTimeStamp() {
        return timeStamp;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTimeStamp(java.util.Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Boolean getSincronizada() {
        return sincronizada;
    }

    public void setSincronizada(Boolean sincronizada) {
        this.sincronizada = sincronizada;
    }

    public long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(long idTarea) {
        this.idTarea = idTarea;
    }

    public Long getIdActa() {
        return idActa;
    }

    public void setIdActa(Long idActa) {
        this.idActa = idActa;
    }

    /** To-one relationship, resolved on first access. */
    public Tarea getTarea() {
        long __key = this.idTarea;
        if (tarea__resolvedKey == null || !tarea__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TareaDao targetDao = daoSession.getTareaDao();
            Tarea tareaNew = targetDao.load(__key);
            synchronized (this) {
                tarea = tareaNew;
            	tarea__resolvedKey = __key;
            }
        }
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        if (tarea == null) {
            throw new DaoException("To-one property 'idTarea' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.tarea = tarea;
            idTarea = tarea.getId();
            tarea__resolvedKey = idTarea;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Acta getActa() {
        Long __key = this.idActa;
        if (acta__resolvedKey == null || !acta__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ActaDao targetDao = daoSession.getActaDao();
            Acta actaNew = targetDao.load(__key);
            synchronized (this) {
                acta = actaNew;
            	acta__resolvedKey = __key;
            }
        }
        return acta;
    }

    public void setActa(Acta acta) {
        synchronized (this) {
            this.acta = acta;
            idActa = acta == null ? null : acta.getId();
            acta__resolvedKey = idActa;
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
