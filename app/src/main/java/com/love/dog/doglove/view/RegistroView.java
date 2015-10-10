package com.love.dog.doglove.view;


import com.love.dog.doglove.ApplicationController;

/**
 * Created by Kevin on 18/09/2015.
 */
public interface RegistroView {

    public void onRegistroCorrecto(int id);
    public void onError(String msg);
    public ApplicationController getApplicationController();

}
