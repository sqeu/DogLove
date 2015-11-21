package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.MensajeDTO;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.GenericView;
import com.love.dog.doglove.view.RegistroView;

/**
 * Created by Hugo on 11/14/2015.
 */
public class RegistroMensajePresenter implements  IRegistroMensajePresenter{
    private static final String url = "http://192.168.1.40:8080/PetLove/RegistroMensajeServlet";
    private GenericView view;

    public RegistroMensajePresenter(GenericView view){
        this.view=view;
    }

    @Override
    public void registrar(String idChat, String mensaje, String idCliente) {
        MensajeDTO mensajeDTO=new MensajeDTO();
        mensajeDTO.setIdChat(idChat);
        mensajeDTO.setIdCliente(idCliente);
        mensajeDTO.setMensaje(mensaje);


        final String json= new Gson().toJson(mensajeDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTO responseDTO = new Gson().fromJson(response, ResponseDTO.class);

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

        stringRequest.setTag("Registro");
        queue.add(stringRequest);
    }
}
