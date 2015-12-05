package com.love.dog.doglove.presenter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.ResponseDTOconPerrosChat;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.ObtenerMascotaChatView;

/**
 * Created by Hugo on 11/27/2015.
 */
public class ObtenerMacotasChatPresenter implements IObtenerMacotasChatPresenter {
    private static final String url = "http://petulima.herokuapp.com/ObtenerMascostasChatServlet";
    private ObtenerMascotaChatView view;

    public ObtenerMacotasChatPresenter (ObtenerMascotaChatView view){
        this.view=view;
    }

    @Override
    public void obtenerMascotasChat(String idMascota) {
        MascotaDTO mascotaDTO= new MascotaDTO();
       mascotaDTO.setIdMascota(idMascota);
        final String json= new Gson().toJson(mascotaDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconPerrosChat responseDTO = new Gson().fromJson(response, ResponseDTOconPerrosChat.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onObtenerCorrecto(responseDTO.getPerros(),responseDTO.getChats());

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000    ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setTag("ObtnerMascotaChat");
        queue.add(stringRequest);

    }


    }

