package test3.ncxchile.cl.greenDAO;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import test3.ncxchile.cl.greenDAO.VehiculoData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table vehiculoData.
*/
public class VehiculoDataDao extends AbstractDao<VehiculoData, Long> {

    public static final String TABLENAME = "vehiculoData";

    /**
     * Properties of entity VehiculoData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property VehiculoID = new Property(1, long.class, "vehiculoID", false, "VEHICULO_ID");
        public final static Property EspeciasID = new Property(2, long.class, "especiasID", false, "ESPECIAS_ID");
        public final static Property ClienteConductorID = new Property(3, long.class, "clienteConductorID", false, "CLIENTE_CONDUCTOR_ID");
        public final static Property ClientePropietarioID = new Property(4, long.class, "clientePropietarioID", false, "CLIENTE_PROPIETARIO_ID");
        public final static Property ParqueaderoID = new Property(5, long.class, "parqueaderoID", false, "PARQUEADERO_ID");
    };

    private DaoSession daoSession;


    public VehiculoDataDao(DaoConfig config) {
        super(config);
    }
    
    public VehiculoDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'vehiculoData' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'VEHICULO_ID' INTEGER NOT NULL ," + // 1: vehiculoID
                "'ESPECIAS_ID' INTEGER NOT NULL ," + // 2: especiasID
                "'CLIENTE_CONDUCTOR_ID' INTEGER NOT NULL ," + // 3: clienteConductorID
                "'CLIENTE_PROPIETARIO_ID' INTEGER NOT NULL ," + // 4: clientePropietarioID
                "'PARQUEADERO_ID' INTEGER NOT NULL );"); // 5: parqueaderoID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'vehiculoData'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, VehiculoData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getVehiculoID());
        stmt.bindLong(3, entity.getEspeciasID());
        stmt.bindLong(4, entity.getClienteConductorID());
        stmt.bindLong(5, entity.getClientePropietarioID());
        stmt.bindLong(6, entity.getParqueaderoID());
    }

    @Override
    protected void attachEntity(VehiculoData entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public VehiculoData readEntity(Cursor cursor, int offset) {
        VehiculoData entity = new VehiculoData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // vehiculoID
            cursor.getLong(offset + 2), // especiasID
            cursor.getLong(offset + 3), // clienteConductorID
            cursor.getLong(offset + 4), // clientePropietarioID
            cursor.getLong(offset + 5) // parqueaderoID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, VehiculoData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVehiculoID(cursor.getLong(offset + 1));
        entity.setEspeciasID(cursor.getLong(offset + 2));
        entity.setClienteConductorID(cursor.getLong(offset + 3));
        entity.setClientePropietarioID(cursor.getLong(offset + 4));
        entity.setParqueaderoID(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(VehiculoData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(VehiculoData entity) {
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getVehiculoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getClienteDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getParqueaderoDao().getAllColumns());
            builder.append(" FROM vehiculoData T");
            builder.append(" LEFT JOIN Vehiculo T0 ON T.'VEHICULO_ID'=T0.'_id'");
            builder.append(" LEFT JOIN cliente T1 ON T.'CLIENTE_CONDUCTOR_ID'=T1.'_id'");
            builder.append(" LEFT JOIN parqueadero T2 ON T.'PARQUEADERO_ID'=T2.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected VehiculoData loadCurrentDeep(Cursor cursor, boolean lock) {
        VehiculoData entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Vehiculo vehiculo = loadCurrentOther(daoSession.getVehiculoDao(), cursor, offset);
         if(vehiculo != null) {
            entity.setVehiculo(vehiculo);
        }
        offset += daoSession.getVehiculoDao().getAllColumns().length;

        Cliente clienteConductor = loadCurrentOther(daoSession.getClienteDao(), cursor, offset);
         if(clienteConductor != null) {
            entity.setClienteConductor(clienteConductor);
        }
        offset += daoSession.getClienteDao().getAllColumns().length;

        Parqueadero parqueadero = loadCurrentOther(daoSession.getParqueaderoDao(), cursor, offset);
         if(parqueadero != null) {
            entity.setParqueadero(parqueadero);
        }

        return entity;    
    }

    public VehiculoData loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<VehiculoData> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<VehiculoData> list = new ArrayList<VehiculoData>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<VehiculoData> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<VehiculoData> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
