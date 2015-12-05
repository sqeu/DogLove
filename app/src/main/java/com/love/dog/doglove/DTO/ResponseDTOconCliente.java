package com.love.dog.doglove.DTO;

/**
 * Created by Hugo on 11/21/2015.
 */
public class ResponseDTOconCliente {
    private String msgStatus;
    private String msgError;
    private UsuarioDTO usuario;


    public ResponseDTOconCliente(String msgStatus, String msgError, UsuarioDTO usuario ) {

        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.usuario=usuario;

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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
