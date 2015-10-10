package com.love.dog.doglove.DTO;

/**
 * Created by Kevin on 18/09/2015.
 */
public class UsuarioDTO {

    private String correo;
    private String password;
    private String nombre;
    private String apellido;

    public UsuarioDTO(){}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
