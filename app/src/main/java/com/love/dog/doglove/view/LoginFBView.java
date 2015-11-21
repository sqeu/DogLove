package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.MascotaDTO;

import java.util.List;

/**
 * Created by Hugo on 11/4/2015.
 */
public interface LoginFBView {
    public void onLoginFBCorrecto(List<MascotaDTO> perros,String idCliente);
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
