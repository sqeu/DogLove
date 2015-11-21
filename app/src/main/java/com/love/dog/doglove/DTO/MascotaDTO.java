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
    private String idFoto;
    private String idMascota;
    private String razaB;
    private String edadB;
    private String distanciaB;

    public MascotaDTO(){}

    public MascotaDTO(String nombre, String raza, String edad) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;

    }

    public String getRazaB() {
        return razaB;
    }

    public void setRazaB(String razaB) {
        this.razaB = razaB;
    }

    public String getEdadB() {
        return edadB;
    }

    public void setEdadB(String edadB) {
        this.edadB = edadB;
    }

    public String getDistanciaB() {
        return distanciaB;
    }

    public void setDistanciaB(String distanciaB) {
        this.distanciaB = distanciaB;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
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
