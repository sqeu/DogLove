package com.love.dog.doglove.DTO;

import java.io.Serializable;

/**
 * Created by Hugo on 10/7/2015.
 */
public class MascotaDTO implements Serializable{

    private String nombre;
    private String raza;
    private String edad;
    private int idcliente;

    public MascotaDTO(){}

    public MascotaDTO(String nombre, String raza, String edad) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

}
