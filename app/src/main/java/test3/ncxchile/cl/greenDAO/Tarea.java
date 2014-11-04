package test3.ncxchile.cl.greenDAO;

import java.util.List;

import de.greenrobot.dao.DaoException;

/**
 * Created by android-developer on 29-10-2014.
 */
public class Tarea {

    private Long id;
    protected Integer servicio;
    protected String fecha;
    protected String tamano;
    protected String direccion;
    protected String comuna;
    protected String estado;
    protected Integer status; // Estado interno

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TareaDao myDao;

    private List<Accion> acciones;


    public Tarea() {
    }

    public Tarea(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tarea(Long id,Integer servicio,String fecha,String tamano,String direccion,String comuna,String estado,Integer status) {
        this.id = id;
        this.servicio=servicio;
        this.fecha=fecha;
        this.tamano=tamano;
        this.direccion=direccion;
        this.comuna=comuna;
        this.estado=estado;
        this.status=status;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTareaDao() : null;
    }

    public Long getId() {
        return id;
    }

    public Integer getServicio() {
        return servicio;
    }

    public void setServicio(int value) {
        this.servicio = value;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String value) {
        this.fecha = value;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String value) {
        this.tamano = value;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String value) {
        this.direccion = value;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String value) {
        this.comuna = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Accion> getAcciones() {
        if (acciones == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AccionDao targetDao = daoSession.getAccionDao();
            List<Accion> accionesNew = targetDao._queryTarea_Acciones(id);
            synchronized (this) {
                if(acciones == null) {
                    acciones = accionesNew;
                }
            }
        }
        return acciones;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetAcciones() {
        acciones = null;
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
