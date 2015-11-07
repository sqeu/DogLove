package com.love.dog.doglove.presenter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ResponseDTOconLlistaMascotas;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.LoginFBView;

/**
 * Created by Hugo on 11/4/2015.
 */
public class LoginFBPresenter implements ILoginFBPresenter{
    private static final String url = "http://192.168.1.40:8080/PetLove/LoginFBServlet";
    private LoginFBView view;

    public LoginFBPresenter (LoginFBView view){
        this.view=view;
    }

    @Override
    public void loginFB(String idFB,String nombre) {
        UsuarioDTO usuario= new UsuarioDTO();
        usuario.setCorreo(idFB);//el token de fb reemplaza el correo
        usuario.setNombre(nombre);
        //la foto del usuario el backend lo guardara a apartir del token

        final String json= new Gson().toJson(usuario);
        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconLlistaMascotas responseDTO = new Gson().fromJson(response, ResponseDTOconLlistaMascotas.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onLoginFBCorrecto(responseDTO.getPerros());

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
