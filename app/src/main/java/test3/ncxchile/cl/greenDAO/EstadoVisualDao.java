package test3.ncxchile.cl.greenDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import test3.ncxchile.cl.greenDAO.EstadoVisual;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EstadoVisual.
*/
public class EstadoVisualDao extends AbstractDao<EstadoVisual, Long> {

    public static final String TABLENAME = "EstadoVisual";

    /**
     * Properties of entity EstadoVisual.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdEstadoVisual = new Property(1, Integer.class, "idEstadoVisual", false, "ID_ESTADO_VISUAL");
        public final static Property Nombre = new Property(2, String.class, "nombre", false, "NOMBRE");
        public final static Property RespuestaBinaria = new Property(3, Boolean.class, "respuestaBinaria", false, "RESPUESTA_BINARIA");
    };


    public EstadoVisualDao(DaoConfig config) {
        super(config);
    }
    
    public EstadoVisualDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EstadoVisual' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ID_ESTADO_VISUAL' INTEGER," + // 1: idEstadoVisual
                "'NOMBRE' TEXT," + // 2: nombre
                "'RESPUESTA_BINARIA' INTEGER);"); // 3: respuestaBinaria
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EstadoVisual'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EstadoVisual entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer idEstadoVisual = entity.getIdEstadoVisual();
        if (idEstadoVisual != null) {
            stmt.bindLong(2, idEstadoVisual);
        }
 
        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(3, nombre);
        }
 
        Boolean respuestaBinaria = entity.getRespuestaBinaria();
        if (respuestaBinaria != null) {
            stmt.bindLong(4, respuestaBinaria ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EstadoVisual readEntity(Cursor cursor, int offset) {
        EstadoVisual entity = new EstadoVisual( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // idEstadoVisual
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nombre
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0 // respuestaBinaria
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EstadoVisual entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdEstadoVisual(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setNombre(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRespuestaBinaria(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EstadoVisual entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EstadoVisual entity) {
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
    
}
