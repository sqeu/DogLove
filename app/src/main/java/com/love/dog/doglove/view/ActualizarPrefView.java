package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;

/**
 * Created by Hugo on 11/10/2015.
 */
public interface ActualizarPrefView {
    public void onRegistroCorrecto();
    public void onError(String msg);
    public ApplicationController getApplicationController();
}
