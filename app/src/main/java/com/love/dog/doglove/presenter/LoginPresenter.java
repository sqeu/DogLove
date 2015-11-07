package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.ResponseDTOconLlistaMascotas;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.LoginView;
import com.love.dog.doglove.view.RegistroView;

/**
 * Created by Hugo on 11/4/2015.
 */
public class LoginPresenter implements ILoginPresenter {
    private static final String url = "http://192.168.1.40:8080/PetLove/LoginServlet";
    private LoginView view;

    public LoginPresenter (LoginView view){
        this.view=view;
    }

    @Override
    public void login(String correo, String password) {

        UsuarioDTO usuario= new UsuarioDTO();
        usuario.setCorreo(correo);
        usuario.setPassword(password);

        final String json= new Gson().toJson(usuario);
        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconLlistaMascotas responseDTO = new Gson().fromJson(response, ResponseDTOconLlistaMascotas.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onLoginCorrecto(responseDTO.getPerros());

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

        stringRequest.setTag("Login");
        queue.add(stringRequest);


    }
}
