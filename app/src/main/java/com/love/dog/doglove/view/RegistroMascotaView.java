package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.MascotaDTO;

import java.util.List;

/**
 * Created by Hugo on 10/7/2015.
 */
public interface RegistroMascotaView {

    public void onRegistroCorrecto(List<MascotaDTO> perros);
    public void onError(String msg);
    public ApplicationController getApplicationController();

}
