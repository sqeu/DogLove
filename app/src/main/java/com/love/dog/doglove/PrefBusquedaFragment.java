package com.love.dog.doglove;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Hugo on 10/24/2015.
 */
public class PrefBusquedaFragment extends Fragment implements View.OnClickListener {

    EditText editEdad;
    Spinner spinRazas,spinDistancia;
    Button buttonAceptar;
    private static final int DISTANCIA_DEFAULT=100;
    private static final String RAZA_DEFAULT="Bulldog";

    public static PrefBusquedaFragment newInstance(){
        return new PrefBusquedaFragment();
    }

    public PrefBusquedaFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_preferencias_busqueda, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editEdad=(EditText)getView().findViewById(R.id.editTextEdadPred);
        spinDistancia=(Spinner)getView().findViewById(R.id.spinnerDistancia);
        spinRazas=(Spinner)getView().findViewById(R.id.spinnerRazaPref);
        buttonAceptar=(Button)getView().findViewById(R.id.buttonAceptarPref);
        buttonAceptar.setOnClickListener(this);
    }

    public void onClick(View v){
        if (v.getId() == R.id.buttonAceptarPref){
            if(editEdad.getText().length()==0){
                System.out.println("no toco edad");
            }
            String distancia=String.valueOf(spinDistancia.getSelectedItem());
            String raza=String.valueOf(spinRazas.getSelectedItem());
            //guardar en BD preferencias aunq sea chancar lo mismo

        }
    }


}
