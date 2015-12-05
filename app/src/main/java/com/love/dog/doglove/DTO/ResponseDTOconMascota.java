package com.love.dog.doglove.DTO;

/**
 * Created by Hugo on 11/21/2015.
 */
public class ResponseDTOconMascota {
    private String msgStatus;
    private String msgError;
    private MascotaDTO mascota;


    public ResponseDTOconMascota(String msgStatus, String msgError, MascotaDTO mascota) {

        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.mascota=mascota;

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

    public MascotaDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaDTO mascota) {
        this.mascota = mascota;
    }
}
