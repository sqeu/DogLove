package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.MensajeDTO;

import java.util.List;

/**
 * Created by Hugo on 11/14/2015.
 */
public interface ObtenerMensajesView {

    public void onObtencionCorrecto(List<MensajeDTO> mensajes);
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
