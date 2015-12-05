package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.ChatDTO;
import com.love.dog.doglove.DTO.MascotaDTO;

import java.util.List;

/**
 * Created by Hugo on 11/27/2015.
 */
public interface ObtenerMascotaChatView {
    public void onObtenerCorrecto(List<MascotaDTO> parejas,List<ChatDTO> chats);
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
