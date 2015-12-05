package com.love.dog.doglove.presenter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.love.dog.doglove.DTO.ChatDTO;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.DTO.ResponseDTOconListaMensajes;
import com.love.dog.doglove.DTO.ResponseDTOconMascota;
import com.love.dog.doglove.view.ObtenerMascotaView;
import com.love.dog.doglove.view.ObtenerMensajesView;

/**
 * Created by Hugo on 11/21/2015.
 */
public class ObtenerPerroPresenter implements IObtenerPerroPresenter {
    private static final String url = "http://petulima.herokuapp.com/ObtenerMascotaServlet";
    private ObtenerMascotaView view;

    public ObtenerPerroPresenter (ObtenerMascotaView view){
        this.view=view;
    }

    @Override
    public void obtenerPerro(String idPerro) {
        MascotaDTO mascotaDTO=new MascotaDTO();
        mascotaDTO.setIdMascota(idPerro);


        final String json= new Gson().toJson(mascotaDTO);

        RequestQueue queue = view.getApplicationController().getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ResponseDTOconMascota responseDTO = new Gson().fromJson(response, ResponseDTOconMascota.class);
                        //System.out.println(responseDTO.getPerros().get(1).getNombre());// PRUEBA

                        if (responseDTO.getMsgStatus().equals("OK")){
                            view.onObtenerCorrecto(responseDTO.getMascota());
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
        stringRequest.setTag("ObtenerMascota");
        queue.add(stringRequest);

    }
}
