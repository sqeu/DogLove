package com.love.dog.doglove.DTO;

import java.util.List;

/**
 * Created by Hugo on 11/27/2015.
 */
public class ResponseDTOconPerrosChat {
    private String msgStatus;
    private String msgError;
    private List<MascotaDTO> perros;
    private List<ChatDTO> chats;

    public ResponseDTOconPerrosChat(String msgStatus, String msgError, List<MascotaDTO> perros, List<ChatDTO> chats) {
        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.perros = perros;
        this.chats = chats;
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


    public List<ChatDTO> getChats() {
        return chats;
    }

    public void setChats(List<ChatDTO> chats) {
        this.chats = chats;
    }

}
