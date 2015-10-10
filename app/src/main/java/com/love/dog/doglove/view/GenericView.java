package com.love.dog.doglove.view;


import com.love.dog.doglove.ApplicationController;

public interface GenericView {
    public void onRegistroCorrecto();
    public void onError(String msg);
    public ApplicationController getApplicationController();




}
