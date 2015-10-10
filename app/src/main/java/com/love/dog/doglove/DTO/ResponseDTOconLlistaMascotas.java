package com.love.dog.doglove.DTO;

import java.util.List;

/**
 * Created by Hugo on 10/7/2015.
 */
public class ResponseDTOconLlistaMascotas {

    private String msgStatus;
    private String msgError;
    private List<MascotaDTO> perros;


    public ResponseDTOconLlistaMascotas(String msgStatus, String msgError, List<MascotaDTO> perros) {
        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.perros = perros;
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

}
