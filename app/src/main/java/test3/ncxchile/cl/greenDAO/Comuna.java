package test3.ncxchile.cl.greenDAO;

/**
 * Created by android-developer on 28-10-2014.
 */
public class Comuna {

    private Long id;
    private String nombre;

    public Comuna() {
    }

    public Comuna(Long id) {
        this.id = id;
    }

    public Comuna(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return this.nombre;
    }

}
