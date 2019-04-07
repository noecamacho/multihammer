package Model;

public class Modulos {
    
    private String id;
    private String nombre;
    private String estado;
    private String activo;

    public Modulos(String id, String nombre, String estado, String activo) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.activo = activo;
    }

    public Modulos() {
        
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
    
    
    
}
