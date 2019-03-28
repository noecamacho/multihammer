package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Usuario extends RecursiveTreeObject<Usuario> {
    
    public SimpleStringProperty id_usuario;
    public SimpleStringProperty usuario;
    public SimpleStringProperty id_perfil;
    public SimpleStringProperty password;
    public SimpleStringProperty estado;
    
    public Usuario(String id_usuario,String usuario,String id_perfil, String password, String estado){
        this.id_usuario = new SimpleStringProperty(id_usuario);
        this.usuario = new SimpleStringProperty(usuario);
        this.id_perfil = new SimpleStringProperty(id_perfil);
        this.password = new SimpleStringProperty(password);
        this.estado = new SimpleStringProperty(estado);
    }
    
    public Usuario(SimpleStringProperty id_usuario,SimpleStringProperty usuario,SimpleStringProperty id_perfil,SimpleStringProperty password,SimpleStringProperty estado){
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.id_perfil = id_perfil;
        this.password = password;
        this.estado=estado;
        
    }

    public Usuario(){
    }
    
    
    
}
