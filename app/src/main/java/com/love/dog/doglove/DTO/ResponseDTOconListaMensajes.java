package com.love.dog.doglove.DTO;

import java.util.List;

/**
 * Created by Hugo on 11/14/2015.
 */
public class ResponseDTOconListaMensajes {
    private String msgStatus;
    private String msgError;
    private List<MensajeDTO> mensajes;

    public ResponseDTOconListaMensajes(String msgStatus, String msgError, List<MensajeDTO> mensajes) {
        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.mensajes = mensajes;
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

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }

}
