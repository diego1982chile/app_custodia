package test3.ncxchile.cl.greenDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by android-developer on 28-10-2014.
 */
public class ComunaDao extends AbstractDao<Comuna, Long> {

    public static final String TABLENAME = "comuna";

    /**
     * Properties of entity Institucion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nombre = new Property(1, String.class, "nombre", false, "NOMBRE");
    };


    public ComunaDao(DaoConfig config) {
        super(config);
    }

    public ComunaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'comuna' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NOMBRE' TEXT);"); // 1: nombre
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'comuna'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Comuna entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String nombre = entity.getNombre();
        if (nombre != null) {
            stmt.bindString(2, nombre);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public Comuna readEntity(Cursor cursor, int offset) {
        Comuna entity = new Comuna( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // nombre
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Comuna entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNombre(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Comuna entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(Comuna entity) {
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
        List instituciones= queryBuilder()
                .list();
        return instituciones;
    }
}
