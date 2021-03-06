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

import test3.ncxchile.cl.greenDAO.Parqueadero;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table parqueadero.
*/
public class ParqueaderoDao extends AbstractDao<Parqueadero, Long> {

    public static final String TABLENAME = "parqueadero";

    /**
     * Properties of entity Parqueadero.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Numero = new Property(1, Integer.class, "numero", false, "NUMERO");
        public final static Property Sector = new Property(2, String.class, "sector", false, "SECTOR");
        public final static Property Servicio = new Property(3, Integer.class, "servicio", false, "SERVICIO");
        public final static Property Recinto = new Property(4, String.class, "recinto", false, "RECINTO");
        public final static Property Disponible = new Property(5, Boolean.class, "disponible", false, "DISPONIBLE");
        public final static Property NumeroEnAgrupador = new Property(6, Integer.class, "numeroEnAgrupador", false, "NUMERO_EN_AGRUPADOR");
        public final static Property AgrupadorID = new Property(7, long.class, "agrupadorID", false, "AGRUPADOR_ID");
        public final static Property AgrupadoPorID = new Property(8, long.class, "agrupadoPorID", false, "AGRUPADO_POR_ID");
        public final static Property FusionadoConID = new Property(9, long.class, "fusionadoConID", false, "FUSIONADO_CON_ID");
    };

    private DaoSession daoSession;


    public ParqueaderoDao(DaoConfig config) {
        super(config);
    }
    
    public ParqueaderoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'parqueadero' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NUMERO' INTEGER," + // 1: numero
                "'SECTOR' TEXT," + // 2: sector
                "'SERVICIO' INTEGER," + // 3: servicio
                "'RECINTO' TEXT," + // 4: recinto
                "'DISPONIBLE' INTEGER," + // 5: disponible
                "'NUMERO_EN_AGRUPADOR' INTEGER," + // 6: numeroEnAgrupador
                "'AGRUPADOR_ID' INTEGER NOT NULL ," + // 7: agrupadorID
                "'AGRUPADO_POR_ID' INTEGER NOT NULL ," + // 8: agrupadoPorID
                "'FUSIONADO_CON_ID' INTEGER NOT NULL );"); // 9: fusionadoConID
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'parqueadero'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Parqueadero entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer numero = entity.getNumero();
        if (numero != null) {
            stmt.bindLong(2, numero);
        }
 
        String sector = entity.getSector();
        if (sector != null) {
            stmt.bindString(3, sector);
        }
 
        Integer servicio = entity.getServicio();
        if (servicio != null) {
            stmt.bindLong(4, servicio);
        }
 
        String recinto = entity.getRecinto();
        if (recinto != null) {
            stmt.bindString(5, recinto);
        }
 
        Boolean disponible = entity.getDisponible();
        if (disponible != null) {
            stmt.bindLong(6, disponible ? 1l: 0l);
        }
 
        Integer numeroEnAgrupador = entity.getNumeroEnAgrupador();
        if (numeroEnAgrupador != null) {
            stmt.bindLong(7, numeroEnAgrupador);
        }
        stmt.bindLong(8, entity.getAgrupadorID());
        stmt.bindLong(9, entity.getAgrupadoPorID());
        stmt.bindLong(10, entity.getFusionadoConID());
    }

    @Override
    protected void attachEntity(Parqueadero entity) {
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
    public Parqueadero readEntity(Cursor cursor, int offset) {
        Parqueadero entity = new Parqueadero( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // numero
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // sector
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // servicio
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // recinto
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // disponible
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // numeroEnAgrupador
            cursor.getLong(offset + 7), // agrupadorID
            cursor.getLong(offset + 8), // agrupadoPorID
            cursor.getLong(offset + 9) // fusionadoConID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Parqueadero entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumero(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setSector(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setServicio(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setRecinto(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDisponible(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setNumeroEnAgrupador(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setAgrupadorID(cursor.getLong(offset + 7));
        entity.setAgrupadoPorID(cursor.getLong(offset + 8));
        entity.setFusionadoConID(cursor.getLong(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Parqueadero entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Parqueadero entity) {
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getAgrupadorDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getParqueaderoSummaryDao().getAllColumns());
            builder.append(" FROM parqueadero T");
            builder.append(" LEFT JOIN agrupador T0 ON T.'AGRUPADOR_ID'=T0.'_id'");
            builder.append(" LEFT JOIN ParqueaderoSummary T1 ON T.'AGRUPADO_POR_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Parqueadero loadCurrentDeep(Cursor cursor, boolean lock) {
        Parqueadero entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Agrupador agrupador = loadCurrentOther(daoSession.getAgrupadorDao(), cursor, offset);
         if(agrupador != null) {
            entity.setAgrupador(agrupador);
        }
        offset += daoSession.getAgrupadorDao().getAllColumns().length;

        ParqueaderoSummary agrupadoPor = loadCurrentOther(daoSession.getParqueaderoSummaryDao(), cursor, offset);
         if(agrupadoPor != null) {
            entity.setAgrupadoPor(agrupadoPor);
        }

        return entity;    
    }

    public Parqueadero loadDeep(Long key) {
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
    public List<Parqueadero> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Parqueadero> list = new ArrayList<Parqueadero>(count);
        
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
    
    protected List<Parqueadero> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Parqueadero> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
