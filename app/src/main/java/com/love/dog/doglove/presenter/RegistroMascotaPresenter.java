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
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.GenericView;
import com.love.dog.doglove.view.RegistroMascotaView;
import com.love.dog.doglove.view.RegistroView;

/**
 * Created by Hugo on 10/7/2015.
 */
public class RegistroMascotaPresenter implements IRegistroMascotaPresenter {
    //cambiar ip deacuerdo al lugar donde se corra
    private static final String url = "http://192.168.241.1:8080/PetLove/RegistroServletMascota";
    private RegistroMascotaView view ;

    public RegistroMascotaPresenter(RegistroMascotaView view){
        this.view=view;
    }

    //Este metodo manda la data al Servlet
    @Override
    public void registrar(String nombre, String raza, String edad,String idCliente) {
        MascotaDTO mascota=new MascotaDTO();
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setIdcliente(Integer.parseInt(idCliente));
        final String json= new Gson().toJson(mascota);

        RequestQueue queue = view.getApplicationController().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconLlistaMascotas responseDTO = new Gson().fromJson(response, ResponseDTOconLlistaMascotas.class);

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onRegistroCorrecto(responseDTO.getPerros());
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
