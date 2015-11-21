package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.MatchDTO;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.RegistroMascotaView;
import com.love.dog.doglove.view.RegistroMatchView;

/**
 * Created by Hugo on 11/7/2015.
 */
public class RegistroMatchPresenter implements IRegistroMatchPresenter {
    private static final String url = "http://192.168.1.40:8080/PetLove/RegistroMatchServlet";
    private RegistroMatchView view ;

    public RegistroMatchPresenter(RegistroMatchView view){
        this.view=view;
    }

    @Override
    public void registrar(String idCliente, String idPareja) {
        MatchDTO matchDTO=new MatchDTO();
        matchDTO.setIdCliente(idCliente);
        matchDTO.setIdPareja(idPareja);

        final String json= new Gson().toJson(matchDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTO responseDTO = new Gson().fromJson(response, ResponseDTO.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onRegistroCorrecto();


                        }else if(responseDTO.getMsgStatus().equals("MATCH")) {
                            view.onMatchEncontrado();
                        }else{

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

        stringRequest.setTag("RegistroMatch");
        queue.add(stringRequest);

    }
}
