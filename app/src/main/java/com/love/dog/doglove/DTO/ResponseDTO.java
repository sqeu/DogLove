package com.love.dog.doglove.DTO;

/**
 * Created by Kevin on 18/09/2015.
 */
public class ResponseDTO {

    private String msgStatus;
    private String msgError;
    private int id;

    public ResponseDTO(String msgStatus, String msgError, int id) {

        this.msgStatus = msgStatus;
        this.msgError = msgError;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
