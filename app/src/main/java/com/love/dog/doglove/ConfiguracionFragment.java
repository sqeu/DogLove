package com.love.dog.doglove;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hugo on 10/24/2015.
 */
public class ConfiguracionFragment extends Fragment {

    public static ConfiguracionFragment newInstance(){
        return new ConfiguracionFragment();
    }

    public ConfiguracionFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_configuracion, container, false);
    }
}
