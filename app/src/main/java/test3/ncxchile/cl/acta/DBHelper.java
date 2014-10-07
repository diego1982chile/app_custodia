package test3.ncxchile.cl.acta;

/**
 * Created by BOBO on 24-07-2014.
 */
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import test3.ncxchile.cl.models.Grupo;
import test3.ncxchile.cl.models.Usuario;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "androcode_ormlite.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Grupo, Integer> grupoDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Grupo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Grupo, Integer> getGrupoDao() throws SQLException {
        if (grupoDao == null) {
            grupoDao = getDao(Grupo.class);
        }
        return grupoDao;
    }

    @Override
    public void close() {
        super.close();
        usuarioDao = null;
        grupoDao = null;
    }

}
