package com.love.dog.doglove.presenter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.ResponseDTOconLlistaMascotas;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.LoginFBView;

/**
 * Created by Hugo on 11/4/2015.
 */
public class LoginFBPresenter implements ILoginFBPresenter{
    private static final String url = "http://petulima.herokuapp.com/LoginFBServlet";
    private LoginFBView view;

    public LoginFBPresenter (LoginFBView view){
        this.view=view;
    }

    @Override
    public void loginFB(String idFB, String nombre, String apellido, String idFoto, String latitud, String longitud,String idGoogle) {
        UsuarioDTO usuario= new UsuarioDTO();
        usuario.setCorreo(idFB);//el token de fb reemplaza el correo
        usuario.setNombre(nombre);
        usuario.setApellido(null);
        usuario.setIdFoto(idFoto);
        usuario.setLatitud(latitud);
        usuario.setLongitud(longitud);
        usuario.setIdGoogle(idGoogle);
        //la foto del usuario el backend lo guardara a apartir del token

        final String json= new Gson().toJson(usuario);
        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconLlistaMascotas responseDTO = new Gson().fromJson(response, ResponseDTOconLlistaMascotas.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onLoginFBCorrecto(responseDTO.getPerros(),responseDTO.getIdPerro()+"",responseDTO.getIdCliente());// este puede ser id del perro o del cleinte

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
        stringRequest.setTag("Login");
        queue.add(stringRequest);
    }
}
