package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ChatDTO;
import com.love.dog.doglove.DTO.MatchDTO;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.ResponseDTOconListaMensajes;
import com.love.dog.doglove.DTO.ResponseDTOconLlistaMascotas;
import com.love.dog.doglove.view.ObtenerMensajesView;

/**
 * Created by Hugo on 11/14/2015.
 */
public class ObtenerMensajesPresenter implements IObtenerMensajesPresenter {
    private static final String url = "http://192.168.1.40:8080/PetLove/ObtenerMensajesServlet";
    private ObtenerMensajesView view;

    public ObtenerMensajesPresenter (ObtenerMensajesView view){
        this.view=view;
    }

    @Override
    public void obtenerMensajes(String idchat) {
        ChatDTO chatDTO=new ChatDTO();
        chatDTO.setIdChat(idchat);

        final String json= new Gson().toJson(chatDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconListaMensajes responseDTO = new Gson().fromJson(response, ResponseDTOconListaMensajes.class);
                        //System.out.println(responseDTO.getPerros().get(1).getNombre());// PRUEBA

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onObtencionCorrecto(responseDTO.getMensajes());
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

        stringRequest.setTag("ObtenerMensajes");
        queue.add(stringRequest);
    }
}
