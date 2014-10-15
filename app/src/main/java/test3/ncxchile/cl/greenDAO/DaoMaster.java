package test3.ncxchile.cl.greenDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import test3.ncxchile.cl.greenDAO.FinalizarActaDao;
import test3.ncxchile.cl.greenDAO.ActaDao;
import test3.ncxchile.cl.greenDAO.VehiculoDataDao;
import test3.ncxchile.cl.greenDAO.VehiculoDao;
import test3.ncxchile.cl.greenDAO.FichaEstadoVisualDao;
import test3.ncxchile.cl.greenDAO.EstadoVisualDao;
import test3.ncxchile.cl.greenDAO.ParqueaderoSummaryDao;
import test3.ncxchile.cl.greenDAO.EspeciasDao;
import test3.ncxchile.cl.greenDAO.ClienteDao;
import test3.ncxchile.cl.greenDAO.ParqueaderoDao;
import test3.ncxchile.cl.greenDAO.AgrupadorDao;
import test3.ncxchile.cl.greenDAO.DireccionDao;
import test3.ncxchile.cl.greenDAO.AutoridadDao;
import test3.ncxchile.cl.greenDAO.PersonaDao;
import test3.ncxchile.cl.greenDAO.CorreosDao;
import test3.ncxchile.cl.greenDAO.TelefonosDao;
import test3.ncxchile.cl.greenDAO.InstitucionDao;
import test3.ncxchile.cl.security.PasswordHelper;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1000): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1002;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        FinalizarActaDao.createTable(db, ifNotExists);
        ActaDao.createTable(db, ifNotExists);
        VehiculoDataDao.createTable(db, ifNotExists);
        VehiculoDao.createTable(db, ifNotExists);
        FichaEstadoVisualDao.createTable(db, ifNotExists);
        EstadoVisualDao.createTable(db, ifNotExists);
        ParqueaderoSummaryDao.createTable(db, ifNotExists);
        EspeciasDao.createTable(db, ifNotExists);
        ClienteDao.createTable(db, ifNotExists);
        ParqueaderoDao.createTable(db, ifNotExists);
        AgrupadorDao.createTable(db, ifNotExists);
        DireccionDao.createTable(db, ifNotExists);
        AutoridadDao.createTable(db, ifNotExists);
        PersonaDao.createTable(db, ifNotExists);
        CorreosDao.createTable(db, ifNotExists);
        TelefonosDao.createTable(db, ifNotExists);
        InstitucionDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        FinalizarActaDao.dropTable(db, ifExists);
        ActaDao.dropTable(db, ifExists);
        VehiculoDataDao.dropTable(db, ifExists);
        VehiculoDao.dropTable(db, ifExists);
        FichaEstadoVisualDao.dropTable(db, ifExists);
        EstadoVisualDao.dropTable(db, ifExists);
        ParqueaderoSummaryDao.dropTable(db, ifExists);
        EspeciasDao.dropTable(db, ifExists);
        ClienteDao.dropTable(db, ifExists);
        ParqueaderoDao.dropTable(db, ifExists);
        AgrupadorDao.dropTable(db, ifExists);
        DireccionDao.dropTable(db, ifExists);
        AutoridadDao.dropTable(db, ifExists);
        PersonaDao.dropTable(db, ifExists);
        CorreosDao.dropTable(db, ifExists);
        TelefonosDao.dropTable(db, ifExists);
        InstitucionDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        Context mContext;

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
            mContext=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            System.out.println("SOY ONCREATE Y ME LLAMARON");
            System.out.println("VOY A CREAR TODAS LAS TABLAS");
            createAllTables(db, true);
            // Aqui se ejecutan los script de poblamiento de BD con datos estaticos
            // Poblar usuario maestro/prueba

            PasswordHelper passwordHelper=new PasswordHelper();

            SQLiteStatement mInsertAttributeStatement = db.compileStatement("INSERT INTO USER (_id, RUT, DV, PASSWORD, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO) VALUES (?,?,?,?,?,?,?)");

            mInsertAttributeStatement.bindLong(1, new Long(1));
            mInsertAttributeStatement.bindLong(2, new Long(11111111));
            mInsertAttributeStatement.bindString(3, "1");
            mInsertAttributeStatement.bindString(4, passwordHelper.encriptarMD5base64("12345").toString());
            mInsertAttributeStatement.bindString(5, "Usuario");
            mInsertAttributeStatement.bindString(6, "de Prueba");
            mInsertAttributeStatement.bindString(7, "CMVRC");

            mInsertAttributeStatement.execute();

            ////////////////////////////////////
            // Poblar instituciones
            InputStream myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("instituciones.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;
            String thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO INSTITUCION (_id, NOMBRE) VALUES (?,?)");
                    mInsertAttributeStatement.bindLong(1, new Long(id));
                    mInsertAttributeStatement.bindString(2, thisLine.toString());
                    mInsertAttributeStatement.execute();
                    ++id;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {                       // always close the file
                if (br != null) try {
                    br.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {

        Context mContext;

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
            mContext=context;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            System.out.println("SOY ONUPGRADE Y ME LLAMARON");
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);

        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(FinalizarActaDao.class);
        registerDaoClass(ActaDao.class);
        registerDaoClass(VehiculoDataDao.class);
        registerDaoClass(VehiculoDao.class);
        registerDaoClass(FichaEstadoVisualDao.class);
        registerDaoClass(EstadoVisualDao.class);
        registerDaoClass(ParqueaderoSummaryDao.class);
        registerDaoClass(EspeciasDao.class);
        registerDaoClass(ClienteDao.class);
        registerDaoClass(ParqueaderoDao.class);
        registerDaoClass(AgrupadorDao.class);
        registerDaoClass(DireccionDao.class);
        registerDaoClass(AutoridadDao.class);
        registerDaoClass(PersonaDao.class);
        registerDaoClass(CorreosDao.class);
        registerDaoClass(TelefonosDao.class);
        registerDaoClass(InstitucionDao.class);
        registerDaoClass(UserDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
