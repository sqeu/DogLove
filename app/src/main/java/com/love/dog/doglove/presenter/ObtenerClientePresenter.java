package com.love.dog.doglove.presenter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.DTO.ResponseDTOconCliente;
import com.love.dog.doglove.DTO.ResponseDTOconMascota;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.view.ObtenerClienteView;
import com.love.dog.doglove.view.ObtenerMascotaView;

/**
 * Created by Hugo on 11/21/2015.
 */
public class ObtenerClientePresenter implements IObtenerClientePresenter{
    private static final String url = "http://petulima.herokuapp.com/ObtenerUsuarioServlet";
    private ObtenerClienteView view;

    public ObtenerClientePresenter (ObtenerClienteView view){
        this.view=view;
    }

    @Override
    public void obtenerCliente(String idUsuario) {
        UsuarioDTO usuarioDTO=new UsuarioDTO();
        usuarioDTO.setIdGoogle(idUsuario);


        final String json= new Gson().toJson(usuarioDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconCliente responseDTO = new Gson().fromJson(response, ResponseDTOconCliente.class);
                        //System.out.println(responseDTO.getPerros().get(1).getNombre());// PRUEBA

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onObtenerCorrecto(responseDTO.getUsuario());
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
        stringRequest.setTag("ObtenerCliente");
        queue.add(stringRequest);
    }

}
