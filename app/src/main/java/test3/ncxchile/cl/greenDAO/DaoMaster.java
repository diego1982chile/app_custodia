package test3.ncxchile.cl.greenDAO;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    public static final int SCHEMA_VERSION = 1184;

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
        ComunaDao.createTable(db, ifNotExists);
        TareaDao.createTable(db, ifNotExists);
        AccionDao.createTable(db, ifNotExists);
        MotivoDao.createTable(db, ifNotExists);
        MotivoFiscaliaDao.createTable(db, ifNotExists);
        TipoVehiculoDao.createTable(db, ifNotExists);
        FirmaDao.createTable(db, ifNotExists);
        LogsDao.createTable(db, ifNotExists);
        SesionDao.createTable(db, ifNotExists);
        MapaDao.createTable(db, ifNotExists);
        UserNameDao.createTable(db, ifNotExists);
        TribunalDao.createTable(db, ifNotExists);
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
        ComunaDao.dropTable(db, ifExists);
        TareaDao.dropTable(db, ifExists);
        AccionDao.dropTable(db, ifExists);
        MotivoDao.dropTable(db, ifExists);
        MotivoFiscaliaDao.dropTable(db, ifExists);
        TipoVehiculoDao.dropTable(db, ifExists);
        FirmaDao.dropTable(db, ifExists);
        LogsDao.dropTable(db, ifExists);
        SesionDao.dropTable(db, ifExists);
        MapaDao.dropTable(db, ifExists);
        UserNameDao.dropTable(db, ifExists);
        TribunalDao.dropTable(db, ifExists);
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
            //System.out.println("SOY ONCREATE Y ME LLAMARON");
            //System.out.println("VOY A CREAR TODAS LAS TABLAS");
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
            // Poblar estadoVisual
            InputStream myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("estadoVisual.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;
            String thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    String[] campos= thisLine.split(";");
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO ESTADO_VISUAL (_id,NOMBRE,RESPUESTA_BINARIA,HABILITADO,TIPO) VALUES (?,?,?,?,?)");
                    mInsertAttributeStatement.bindLong(1, Long.parseLong(campos[0]));
                    mInsertAttributeStatement.bindString(2, campos[1].toString());
                    mInsertAttributeStatement.bindString(3, campos[2].toString());
                    mInsertAttributeStatement.bindLong(4, Long.parseLong(campos[3]));
                    mInsertAttributeStatement.bindLong(5, Long.parseLong(campos[4]));
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


            ////////////////////////////////////
            // Poblar instituciones
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("instituciones.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    String[] campos= thisLine.split(";");
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO INSTITUCION (_id, NOMBRE) VALUES (?,?)");
                    mInsertAttributeStatement.bindLong(1, Long.parseLong(campos[0]));
                    mInsertAttributeStatement.bindString(2, campos[1].toString());
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

            ////////////////////////////////////
            // Poblar tribunales
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("tribunales.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    String[] campos= thisLine.split(";");
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO TRIBUNAL (_id, NOMBRE) VALUES (?,?)");
                    mInsertAttributeStatement.bindLong(1, Long.parseLong(campos[0]));
                    mInsertAttributeStatement.bindString(2, campos[1].toString());
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

            ////////////////////////////////////
            // Poblar comunas
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("comunas.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO COMUNA (_id, NOMBRE) VALUES (?,?)");
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


            ////////////////////////////////////
            // Poblar tareas
            myInput=null;
            InputStream myInputAutoridad=null;
            BufferedReader brAutoridad = null;
            String thisLineAutoridad = null;

            InputStream myInputVehiculo=null;
            BufferedReader brVehiculo= null;
            String thisLineVehiculo = null;

            InputStream myInputRetiro=null;
            BufferedReader brRetiro= null;
            String thisLineRetiro = null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("tareas.txt");
                myInputAutoridad = mContext.getAssets().open("autoridades.json");
                myInputVehiculo = mContext.getAssets().open("vehiculos.json");
                myInputRetiro = mContext.getAssets().open("retiros.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            String resultAutoridad=null;
            String resultVehiculo=null;
            String resultRetiro=null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                brAutoridad = new BufferedReader((new InputStreamReader(myInputAutoridad)));
                brVehiculo = new BufferedReader((new InputStreamReader(myInputVehiculo)));
                brRetiro = new BufferedReader((new InputStreamReader(myInputRetiro)));
                long id = 1;

                thisLineAutoridad = brAutoridad.readLine();
                System.out.println(thisLineAutoridad);
                thisLineVehiculo = brVehiculo.readLine();
                thisLineRetiro = brRetiro.readLine();

                while ((thisLine = br.readLine()) != null ) {

                    System.out.println("id="+id);

                    String[] campos= thisLine.split(";");
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO TAREA (_id, SERVICIO, FECHA, TAMANO, DIRECCION, COMUNA, ESTADO, " +
                            "STATUS) VALUES (?,?,?,?,?,?,?,?)");
                    mInsertAttributeStatement.bindLong(1, new Long(id));
                    mInsertAttributeStatement.bindLong(2, Integer.parseInt(campos[0]));
                    mInsertAttributeStatement.bindString(3, campos[1].toString());
                    mInsertAttributeStatement.bindString(4, campos[2].toString());
                    mInsertAttributeStatement.bindString(5, campos[3].toString());
                    mInsertAttributeStatement.bindString(6, campos[4].toString());
                    mInsertAttributeStatement.bindString(7, campos[5].toString());
                    mInsertAttributeStatement.bindLong(8, 0);
                    //mInsertAttributeStatement.execute();
                    Long tareaId= mInsertAttributeStatement.executeInsert();

                    ////////////////////// Insertar datos de solicitud en el acta asociada a esta tarea ///////////////////////
                    /*

                    StringBuilder sb = new StringBuilder();
                    sb.append(thisLineAutoridad+"\n");
                    resultAutoridad= sb.toString();

                    sb = new StringBuilder();
                    sb.append(thisLineVehiculo+"\n");
                    resultVehiculo= sb.toString();

                    sb = new StringBuilder();
                    sb.append(thisLineRetiro+"\n");
                    resultRetiro= sb.toString();

                    try {
                        JSONObject jsonObject= new JSONObject(resultAutoridad);
                        JSONArray jsonArray = jsonObject.getJSONArray("autoridades");

                        JSONObject oneObject = jsonArray.getJSONObject((int)id-1);

                        SQLiteStatement mIdStatement = db.compileStatement("SELECT max(_id)+1 from DIRECCION");

                        // Insertar direccion
                        mInsertAttributeStatement = db.compileStatement("INSERT INTO DIRECCION (_id, CALLE, NUMERACION, INTERSECCION, REFERENCIAS, " +
                                "COMUNA) VALUES (?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("calle"));
                        mInsertAttributeStatement.bindString(3, oneObject.getString("numeracion"));
                        mInsertAttributeStatement.bindString(4, oneObject.getString("interseccion"));
                        mInsertAttributeStatement.bindString(5, oneObject.getString("referencia"));
                        mInsertAttributeStatement.bindString(6, oneObject.getString("comuna"));
                        //Long direccionId= mInsertAttributeStatement.execute();
                        Long direccionId= mInsertAttributeStatement.executeInsert();

                        SQLiteStatement mIdStatementPersona = db.compileStatement("SELECT max(_id)+1 from PERSONA");

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from CORREOS");

                        // Insertar correo
                        mInsertAttributeStatement = db.compileStatement("INSERT INTO CORREOS (_id, EMAIL, CORREOS_ID) VALUES (?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("correo"));
                        mInsertAttributeStatement.bindLong(3, mIdStatementPersona.simpleQueryForLong());
                        //mInsertAttributeStatement.execute();
                        Long correoId= mInsertAttributeStatement.executeInsert();

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from TELEFONOS");

                        // Insertar telefono
                        mInsertAttributeStatement = db.compileStatement("INSERT INTO TELEFONOS (_id, EMAIL, TELEFONOS_ID) VALUES (?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("fono"));
                        mInsertAttributeStatement.bindLong(3, mIdStatementPersona.simpleQueryForLong());
                        //mInsertAttributeStatement.execute();
                        Long fonoId= mInsertAttributeStatement.executeInsert();

                        // Insertar persona
                        mInsertAttributeStatement = db.compileStatement("INSERT INTO PERSONA (_id, NOMBRE, RUT, APELLIDO_PATERNO, APELLIDO_MATERNO," +
                                " USUARIO, CORREOS_ID, TELEFONOS_ID, DIRECCION2_ID) VALUES (?,?,?,?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatementPersona.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("nombres"));
                        mInsertAttributeStatement.bindString(3, oneObject.getString("rut")+oneObject.getString("dv"));
                        mInsertAttributeStatement.bindString(4, oneObject.getString("apellido_paterno"));
                        mInsertAttributeStatement.bindString(5, oneObject.getString("apellido_materno"));
                        mInsertAttributeStatement.bindString(6, "");
                        mInsertAttributeStatement.bindLong(7, correoId);
                        mInsertAttributeStatement.bindLong(8, fonoId);
                        mInsertAttributeStatement.bindLong(9, direccionId);
                        //mInsertAttributeStatement.execute();
                        Long personaId= mInsertAttributeStatement.executeInsert();

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from AUTORIDAD");

                        // Insertar Autoridad

                        mInsertAttributeStatement = db.compileStatement("INSERT INTO AUTORIDAD (_id, CARGO, INSTITUCION, UNIDAD_POLICIAL, NUMERO_FUNCIONARIO, " +
                                "PERSONA_ID) VALUES (?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("cargo"));
                        mInsertAttributeStatement.bindString(3, oneObject.getString("institucion"));
                        mInsertAttributeStatement.bindString(4, oneObject.getString("unidad_policial"));
                        mInsertAttributeStatement.bindString(5, oneObject.getString("numero_funcionario"));
                        mInsertAttributeStatement.bindLong(6, personaId);
                        //mInsertAttributeStatement.execute();
                        Long autoridadId= mInsertAttributeStatement.executeInsert();

                        jsonObject= new JSONObject(resultVehiculo);
                        jsonArray = jsonObject.getJSONArray("vehiculos");
                        oneObject = jsonArray.getJSONObject((int)id-1);

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from VEHICULO");

                        // Insertar vehículo

                        mInsertAttributeStatement = db.compileStatement("INSERT INTO VEHICULO (_id, MARCA, MODELO, ANIO, COLOR, " +
                                "MATRICULA, MODIFICADO, CARACTERISTICAS, NUMERO_CHASIS, NUMERO_MOTOR, TAMANO, KILOMETRAJE, " +
                                "CARPETA_VEHICULO, SERVICIO, VIN, ORIGEN_VEHICULO, PUEDE_RODAR, CLONADO, FICHA_ESTADOVISUAL_ID, " +
                                "PARQUEADERO_SUMMARY_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindString(2, oneObject.getString("marca"));
                        mInsertAttributeStatement.bindString(3, oneObject.getString("modelo"));
                        mInsertAttributeStatement.bindString(4, oneObject.getString("anio"));
                        mInsertAttributeStatement.bindString(5, oneObject.getString("color"));
                        mInsertAttributeStatement.bindString(6, oneObject.getString("matricula"));
                        mInsertAttributeStatement.bindLong(7, 0);
                        mInsertAttributeStatement.bindString(8, oneObject.getString("caracteristicas"));
                        mInsertAttributeStatement.bindString(9, "");
                        mInsertAttributeStatement.bindString(10, "");
                        mInsertAttributeStatement.bindString(11, oneObject.getString("tamano"));
                        mInsertAttributeStatement.bindString(12, "");
                        mInsertAttributeStatement.bindString(13, oneObject.getString("ficha_vehiculo"));
                        mInsertAttributeStatement.bindString(14, "");
                        mInsertAttributeStatement.bindString(15, "");
                        mInsertAttributeStatement.bindString(16, "");
                        mInsertAttributeStatement.bindString(17, oneObject.getString("puede_rodar"));
                        mInsertAttributeStatement.bindString(18, "");
                        mInsertAttributeStatement.bindLong(19, 0);
                        mInsertAttributeStatement.bindLong(20, 0);
                        //mInsertAttributeStatement.execute();
                        Long vehiculoId= mInsertAttributeStatement.executeInsert();

                        // Insertar VehiculoData

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from VEHICULODATA");

                        mInsertAttributeStatement = db.compileStatement("INSERT INTO VEHICULODATA (_id, VEHICULO_ID, ESPECIAS_ID, " +
                                "CLIENTE_CONDUCTOR_ID, CLIENTE_PROPIETARIO_ID, PARQUEADERO_ID) VALUES (?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, mIdStatement.simpleQueryForLong());
                        mInsertAttributeStatement.bindLong(2, vehiculoId);
                        mInsertAttributeStatement.bindLong(3, 0);
                        mInsertAttributeStatement.bindLong(4, 0);
                        mInsertAttributeStatement.bindLong(5, 0);
                        mInsertAttributeStatement.bindLong(6, 0);
                        //mInsertAttributeStatement.execute();
                        Long vehiculoDataId= mInsertAttributeStatement.executeInsert();

                        jsonObject= new JSONObject(resultRetiro);
                        jsonArray = jsonObject.getJSONArray("retiros");
                        oneObject = jsonArray.getJSONObject((int)id-1);

                        // Insertar direccion del retiro

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from DIRECCION");

                        mInsertAttributeStatement = db.compileStatement("INSERT INTO DIRECCION (_id, CALLE, NUMERACION, INTERSECCION, REFERENCIAS, " +
                                "COMUNA) VALUES (?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, direccionId+1);
                        mInsertAttributeStatement.bindString(2, oneObject.getString("calle"));
                        mInsertAttributeStatement.bindString(3, oneObject.getString("numeracion"));
                        mInsertAttributeStatement.bindString(4, oneObject.getString("interseccion"));
                        mInsertAttributeStatement.bindString(5, oneObject.getString("referencia"));
                        mInsertAttributeStatement.bindString(6, oneObject.getString("comuna"));
                        //Long direccionId= mInsertAttributeStatement.execute();
                        Long direccionRetiroId= mInsertAttributeStatement.executeInsert();

                        // Insertar Acta

                        mIdStatement = db.compileStatement("SELECT max(_id)+1 from ACTA");

                        mInsertAttributeStatement = db.compileStatement("INSERT INTO ACTA (_id, OBSERVACION, CAUSA_RETIRO, EXIST_IMAGE, " +
                                "EXIST_VIDEO, FECHA_CREACION, FECHA_FIRMA, ID_SOLICITUD, ID_OT, ID_GRUA, FISCALIA, NUE, RUC, PARTE, UNIDAD_POLICIAL," +
                                " FECHA_PARTE, SERVICIO, GRUA_EXTERNA, OBSERVACION_IMGENES, NOMBRE_EXTERNO, NUMERO_FACTURA, MONTO_FACTURA, NUMERO_PATENTE," +
                                " CARGA_INICIAL, ACTA_INCAUTACION, OFICIO_REMISOR, ACTA_JSON, VEHICULO_DATA_ID, DIRECCION_ID, AUTORIDAD_ID, GRUERO_ID," +
                                " TRIBUNAL_ID, TAREA_ID ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        mInsertAttributeStatement.bindLong(1, new Long(id));
                        mInsertAttributeStatement.bindString(2, oneObject.getString("observacion")); // observacion
                        mInsertAttributeStatement.bindString(3, oneObject.getString("motivo"));
                        mInsertAttributeStatement.bindLong(4, 0); //private Boolean existImage;
                        mInsertAttributeStatement.bindLong(5, 0); //private Boolean existVideo;
                        mInsertAttributeStatement.bindString(6, ""); //private java.util.Date fechaCreacion;
                        mInsertAttributeStatement.bindString(7, ""); //private java.util.Date fechaFirma;
                        mInsertAttributeStatement.bindLong(8, 0); //private Integer idSolicitud;
                        mInsertAttributeStatement.bindLong(9, 0); //private Integer idOt;
                        mInsertAttributeStatement.bindLong(10, 0); //private Integer idGrua;
                        mInsertAttributeStatement.bindLong(11, oneObject.getString("fiscalia").equals("")? 0:1); //private Boolean fiscalia;
                        mInsertAttributeStatement.bindString(12, ""); //private String nue;
                        mInsertAttributeStatement.bindString(13, ""); //private String ruc;
                        mInsertAttributeStatement.bindString(14, ""); //private String parte;
                        mInsertAttributeStatement.bindString(15, ""); //private String unidadPolicial;
                        mInsertAttributeStatement.bindString(16, ""); //private java.util.Date fechaParte;
                        mInsertAttributeStatement.bindLong(17, 0); //private Integer servicio;
                        mInsertAttributeStatement.bindLong(18, 0); //private Boolean gruaExterna;
                        mInsertAttributeStatement.bindString(19, ""); //private String observacionImgenes;
                        mInsertAttributeStatement.bindString(20, ""); //private String nombreExterno;
                        mInsertAttributeStatement.bindLong(21, 0); //private Integer numeroFactura;
                        mInsertAttributeStatement.bindLong(22, 0); //private Integer montoFactura;
                        mInsertAttributeStatement.bindString(23, ""); //private String numeroPatente;
                        mInsertAttributeStatement.bindString(24, ""); //private Boolean cargaInicial;
                        mInsertAttributeStatement.bindString(25, ""); //private String actaIncautacion;
                        mInsertAttributeStatement.bindString(26, ""); //private String oficioRemisor;
                        mInsertAttributeStatement.bindString(27, oneObject.getString("actaJson")); //private String oficioRemisor;
                        mInsertAttributeStatement.bindLong(28, vehiculoDataId);
                        mInsertAttributeStatement.bindLong(29, direccionRetiroId);
                        mInsertAttributeStatement.bindLong(30, autoridadId);
                        mInsertAttributeStatement.bindLong(31, 0);
                        mInsertAttributeStatement.bindLong(32, 0);
                        mInsertAttributeStatement.bindLong(33, tareaId);
                        //mInsertAttributeStatement.bindLong(33, null);
                        //mInsertAttributeStatement.execute();
                        Long actaId= mInsertAttributeStatement.executeInsert();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    */
                    ++id;
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {                       // always close the file
                if (br != null) try {
                    br.close();
                    brAutoridad.close();
                    brVehiculo.close();
                    brRetiro.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }

            ////////////////////////////////////
            // Poblar motivos
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("motivos.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO MOTIVO (_id, NOMBRE) VALUES (?,?)");
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

            ////////////////////////////////////
            // Poblar motivos fiscalia
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("motivos_fiscalia.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO MOTIVO_FISCALIA (_id, NOMBRE) VALUES (?,?)");
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

            ////////////////////////////////////
            // Poblar tipos vehiculo
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("tipos_vehiculo.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    mInsertAttributeStatement = db.compileStatement("INSERT INTO TIPO_VEHICULO (_id, NOMBRE) VALUES (?,?)");
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


            ////////////////////////////////////
            // Poblar users
            myInput=null;

            //System.out.print("ASSETS1="+getAssets().toString());
            try {
                myInput = mContext.getAssets().open("users.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            br = null;
            thisLine = null;

            try {
                br = new BufferedReader((new InputStreamReader(myInput)));
                long id = 1;
                while ((thisLine = br.readLine()) != null) {
                    String[] campos= thisLine.split(";");

                    mInsertAttributeStatement = db.compileStatement("INSERT INTO USER_NAME (_id, RUT, USER_NAME) VALUES (?,?,?)");
                    mInsertAttributeStatement.bindLong(1, new Long(id));
                    mInsertAttributeStatement.bindString(2, campos[0].toString());
                    mInsertAttributeStatement.bindString(3, campos[1].toString());
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
            //System.out.println("SOY ONUPGRADE Y ME LLAMARON");
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
        registerDaoClass(ComunaDao.class);
        registerDaoClass(TareaDao.class);
        registerDaoClass(AccionDao.class);
        registerDaoClass(MotivoDao.class);
        registerDaoClass(MotivoFiscaliaDao.class);
        registerDaoClass(TipoVehiculoDao.class);
        registerDaoClass(FirmaDao.class);
        registerDaoClass(LogsDao.class);
        registerDaoClass(SesionDao.class);
        registerDaoClass(MapaDao.class);
        registerDaoClass(UserNameDao.class);
        registerDaoClass(TribunalDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
