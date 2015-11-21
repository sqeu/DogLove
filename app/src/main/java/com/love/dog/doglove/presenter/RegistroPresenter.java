package com.love.dog.doglove.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ResponseDTO;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.RegistroView;


/**
 * Created by Kevin on 18/09/2015.
 */
public class RegistroPresenter implements IRegistroPresenter{

    //cambiar ip deacuerdo al lugar donde se corra
    private static final String url = "http://192.168.1.40:8080/PetLove/RegistroServlet";
    private RegistroView view;

    public RegistroPresenter(RegistroView view){
        this.view=view;
    }

    //Este metodo manda la data al Servlet
    public void registrar(String correo, String password, String nombre, String apellido,String idFoto,String latitud, String longitud,String idGoogle){

        UsuarioDTO usuario= new UsuarioDTO();
        usuario.setCorreo(correo);
        usuario.setPassword(password);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setIdFoto(idFoto);
        usuario.setLatitud(latitud);
        usuario.setLongitud(longitud);
        usuario.setIdGoogle(idGoogle);
        final String json= new Gson().toJson(usuario);

        RequestQueue queue = view.getApplicationController().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTO responseDTO = new Gson().fromJson(response, ResponseDTO.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onRegistroCorrecto(responseDTO.getId());

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
