package com.love.dog.doglove.view;

import com.love.dog.doglove.ApplicationController;

/**
 * Created by Hugo on 11/7/2015.
 */
public interface RegistroMatchView {
    public void onRegistroCorrecto();
    public void onError(String msg);
    public void onMatchEncontrado();
    public ApplicationController getApplicationController();

}
