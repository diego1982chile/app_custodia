package test3.ncxchile.cl.greenDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by android-developer on 29-10-2014.
 */
public class TareaDao extends AbstractDao<Tarea, Long> {

    public static final String TABLENAME = "tarea";

    /**
     * Properties of entity Institucion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Servicio = new Property(1, Integer.class, "servicio", false, "SERVICIO");
        public final static Property Fecha = new Property(2, String.class, "fecha", false, "FECHA");
        public final static Property Tamano = new Property(3, String.class, "tamano", false, "TAMANO");
        public final static Property Direccion = new Property(4, String.class, "direccion", false, "DIRECCION");
        public final static Property Comuna = new Property(5, String.class, "comuna", false, "COMUNA");
        public final static Property Estado = new Property(6, String.class, "estado", false, "ESTADO");
        public final static Property Status = new Property(7, Integer.class, "status", false, "STATUS");
    };


    public TareaDao(DaoConfig config) {
        super(config);
    }

    public TareaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'tarea' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SERVICIO' INTEGER ," + // 0: id
                "'FECHA' TEXT ," +
                "'TAMANO' TEXT ," +
                "'DIRECCION' TEXT ," +
                "'COMUNA' TEXT ," +
                "'ESTADO' TEXT ," +
                "'STATUS' INTEGER);"
                ); // 1: nombre
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'tarea'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Tarea entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer servicio = entity.getServicio();
        if (servicio != null) {
            stmt.bindLong(2, servicio);
        }

        String fecha = entity.getFecha();
        if (fecha != null) {
            stmt.bindString(3, fecha);
        }

        String tamano = entity.getTamano();
        if (tamano != null) {
            stmt.bindString(4, tamano);
        }

        String direccion = entity.getDireccion();
        if (direccion != null) {
            stmt.bindString(5, direccion);
        }

        String comuna = entity.getComuna();
        if (comuna != null) {
            stmt.bindString(6, comuna);
        }

        String estado = entity.getEstado();
        if (estado != null) {
            stmt.bindString(7, estado);
        }

        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(8, status);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public Tarea readEntity(Cursor cursor, int offset) {
        Tarea entity = new Tarea( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // servicio
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // servicio
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // servicio
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // servicio
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // servicio
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // servicio
                cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7) // servicio
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Tarea entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setServicio(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setFecha(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTamano(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDireccion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setComuna(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEstado(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStatus(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Tarea entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(Tarea entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    public List getAll(){
        List tareas= queryBuilder()
                .list();
        return tareas;
    }

    public List getAsignadas(){
        List tareas= queryBuilder()
                .where(Properties.Estado.eq("\"Asignada\""))
                .list();
        return tareas;
    }

    public Tarea getById(Integer idTarea){
        return queryBuilder()
                .where(Properties.Id.eq(idTarea))
                .unique();
    }

    public Integer getStatusTarea(long idTarea){
        Tarea tarea= queryBuilder()
                .where(Properties.Id.eq(idTarea))
                .unique();
        return tarea.getStatus();
    }

    public void setStatusTarea(long idTarea, Integer status){
        Tarea tarea= queryBuilder()
                .where(Properties.Id.eq(idTarea))
                .unique();
        if(tarea!=null) {
            tarea.setStatus(status);
            update(tarea);
            refresh(tarea);
        }
        return;
    }
}
