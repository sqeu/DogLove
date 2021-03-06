package com.love.dog.doglove.DTO;

import java.util.List;

/**
 * Created by Hugo on 10/7/2015.
 */
public class ResponseDTOconLlistaMascotas {

    private String msgStatus;
    private String msgError;
    private List<MascotaDTO> perros;
    private String idPerro;
    private String idCliente;

    public ResponseDTOconLlistaMascotas(String msgStatus, String msgError, List<MascotaDTO> perros,String idPerro, String idCliente) {
        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.perros = perros;
        this.idPerro=idPerro;
        this.idCliente=idCliente;

    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public List<MascotaDTO> getPerros() {
        return perros;
    }

    public void setPerros(List<MascotaDTO> perros) {
        this.perros = perros;
    }

    public String getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(String idPerro) {
        this.idPerro = idPerro;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
