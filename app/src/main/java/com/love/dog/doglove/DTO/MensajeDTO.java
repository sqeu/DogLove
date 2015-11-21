package com.love.dog.doglove.DTO;

/**
 * Created by Hugo on 11/14/2015.
 */
public class MensajeDTO {

    private String idChat;
    private String mensaje;
    private String idCliente;

    public MensajeDTO() {
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}
