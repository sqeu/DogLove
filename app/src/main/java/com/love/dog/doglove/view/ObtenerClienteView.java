package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;
import com.love.dog.doglove.DTO.UsuarioDTO;

/**
 * Created by Hugo on 11/21/2015.
 */
public interface ObtenerClienteView {
    public void onObtenerCorrecto(UsuarioDTO usuarioDTO);
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
