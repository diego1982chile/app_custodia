package test3.ncxchile.cl.greenDAO;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table MARCA.
 */
public class Marca {

    private Long id;
    private String nombre;

    public Marca() {
    }

    public Marca(Long id) {
        this.id = id;
    }

    public Marca(Long id, String nombre) {
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
