package test3.ncxchile.cl.widgets;

/**
 * Created by android-developer on 15-10-2014.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.Institucion;

public class DatabaseHandler{

    private static SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context localContext;
    private String table="";

    // constructor
    public DatabaseHandler(Context context) {
        localContext=context;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(localContext,"cmvrc_android", null);
        db = helper.getWritableDatabase();
        /*
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        List<Institucion> instituciones=daoSession.getInstitucionDao().getAll();

        System.out.println("Instituciones en la tabla Institucion:");
        for (Iterator<Institucion> iter = instituciones.iterator(); iter.hasNext(); ) {
            Institucion institucion = iter.next();
            //System.out.println(institucion.getNombre());
        }
        */
    }

    public void setTable(String table){
        this.table=table;
    }

    // Read records related to the search term
    public List<Institucion> read(String searchTerm) {

        List<Institucion> recordsList = new ArrayList<Institucion>();

        // select query
        String sql = "";
        sql += "SELECT * FROM "+table;
        sql += " WHERE NOMBRE LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY NOMBRE ASC";
        //sql += " LIMIT 0,5";

        //System.out.println("sql="+sql);
        // Inicializar UserDao

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                Institucion institucion = new Institucion(cursor.getLong(0),cursor.getString(1));
                // add to list
                recordsList.add(institucion);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }
}
