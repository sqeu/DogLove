package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.MascotaDTO;

/**
 * Created by Hugo on 11/21/2015.
 */
public interface ObtenerMascotaView {
    public void onObtenerCorrecto(MascotaDTO mascotaDTO);
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
