package co.woofy.upload;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table MOTIVO.
 */
public class Motivo {

    private Long id;
    private String nombre;

    public Motivo() {
    }

    public Motivo(Long id) {
        this.id = id;
    }

    public Motivo(Long id, String nombre) {
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

}
