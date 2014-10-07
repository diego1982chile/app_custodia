package test3.ncxchile.cl.models;

/**
 * Created by BOBO on 24-07-2014.
 */
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Usuario {

    public static final String ID = "_id";
    public static final String NOMBRE = "nombre";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String GRUPO = "grupo";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(columnName = NOMBRE)
    private String nombre;
    @DatabaseField(columnName = FECHA_NACIMIENTO)
    private Date fechaNacimiento;
    @DatabaseField(foreign = true, columnName = GRUPO)
    private Grupo grupo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
