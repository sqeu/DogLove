package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.ResponseDTOconLlistaMascotas;
import com.love.dog.doglove.view.ActualizarPrefView;

/**
 * Created by Hugo on 11/10/2015.
 */
public class ActualizarPrefPresenter implements IActualizarPrefPresenter {
    private static final String url = "http://192.168.1.40:8080/PetLove/ActualizarPrefServlet";
    private ActualizarPrefView view ;

    public ActualizarPrefPresenter(ActualizarPrefView view){
        this.view=view;
    }

    @Override
    public void actualizar(String idMascota, String razaB, String edadB, String distanciaB) {
        MascotaDTO mascota=new MascotaDTO();
        mascota.setIdMascota(idMascota);
        mascota.setRazaB(razaB);
        mascota.setEdadB(edadB);
        mascota.setDistanciaB(distanciaB);
        final String json= new Gson().toJson(mascota);

        RequestQueue queue = view.getApplicationController().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTO responseDTO = new Gson().fromJson(response, ResponseDTO.class);
                        //System.out.println(responseDTO.getPerros().get(1).getNombre());// PRUEBA

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onRegistroCorrecto();
                            /*
                        }else if (responseDTO.getMsgStatus().equals("ERROR")){
                            view.onRegistroIncorrecto();
                            */
                        }
                        else{
                            view.onError(responseDTO.getMsgError());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onError("RegistroPresenter (5XX): " + error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody(){
                return json.getBytes();
            }
        };

        stringRequest.setTag("ActualizarPref");
        queue.add(stringRequest);
    }
}
