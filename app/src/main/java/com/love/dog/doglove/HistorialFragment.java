package com.love.dog.doglove;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.love.dog.doglove.DTO.ChatDTO;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.presenter.IObtenerMacotasChatPresenter;
import com.love.dog.doglove.presenter.ObtenerMacotasChatPresenter;
import com.love.dog.doglove.view.ObtenerMascotaChatView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 10/24/2015.
 */
public class HistorialFragment extends Fragment implements ObtenerMascotaChatView {
    public static HistorialFragment newInstance() {
        return new HistorialFragment();
    }

    LayoutInflater inflater;

    List<ChatDTO> chats;

    public HistorialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_historial_chat, container, false);
    }

    /**
     * The Chat list.
     */
    private ArrayList<MascotaDTO> mList;

    /**
     * The user.
     */
    public MascotaDTO mascota;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        updateUserStatus(true);
        mascota = new MascotaDTO();
        Intent intent = getActivity().getIntent();
        String idMascota = intent.getStringExtra("idPerro");
        String idDueno = intent.getStringExtra("id");
        System.out.println("historial:idmascota: " + idMascota + "::idDUeno:" + idDueno);

        mascota.setIdMascota(idMascota);
        mascota.setIdcliente(Integer.valueOf(idDueno));
        IObtenerMacotasChatPresenter obt = new ObtenerMacotasChatPresenter(this);
        obt.obtenerMascotasChat(idMascota);
    }


    //footo
    public void setImagen(String idFoto, final CircularImageView lbl) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ImagenMascota");

        // Locate the objectId from the class
        query.getInBackground(idFoto,
                new GetCallback<ParseObject>() {

                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        ParseFile fileObject = (ParseFile) object
                                .get("ImageFile");
                        fileObject
                                .getDataInBackground(new GetDataCallback() {

                                    @Override
                                    public void done(byte[] data, com.parse.ParseException e) {
                                        if (e == null) {
                                            Log.d("test",
                                                    "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            Bitmap bmp = BitmapFactory
                                                    .decodeByteArray(
                                                            data, 0,
                                                            data.length);
                                            lbl.setImageBitmap(bmp);
                                            /*Drawable d = new BitmapDrawable(Resources.getSystem(), bmp);
                                            lbl.setCompoundDrawablesWithIntrinsicBounds(d, null, ContextCompat.getDrawable(getView().getContext(), R.drawable.arrow),null);*/
                                            //imagen.setImageBitmap(bmp);
                                            // Close progress dialog
                                            // progressDialog.dismiss();

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }

                                });
                    }

                });


    }
    ///

    @Override
    public void onObtenerCorrecto(List<MascotaDTO> parejas, List<ChatDTO> chats) {

        this.chats = chats;
        loadUserList(parejas);

    }

    @Override
    public void onError(String msg) {
        Toast.makeText(
                getActivity(),
                "EXC: " + msg,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public ApplicationController getApplicationController() {
        return (ApplicationController) getActivity().getApplication();

    }

    /**
     * Load list of users.
     */
    private void loadUserList(List<MascotaDTO> listaChat) {

        mList = new ArrayList<MascotaDTO>(listaChat);
        ListView list = (ListView) getView().findViewById(R.id.list);
        list.setAdapter(new UserAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int pos, long arg3) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ContenedorActivity.class);
                intent.putExtra("id", mascota.getIdcliente() + "");
                intent.putExtra("idPerro", mascota.getIdMascota());
                intent.putExtra("idchat", chats.get(pos).getIdChat());
                intent.putExtra("nombreUsuario", mList.get(pos).getNombre());
                intent.putExtra("idfoto2",mList.get(pos).getIdFoto());
                //System.out.println("1 : " +chats.get(pos).getIdChat() + " :: 2 : " +  mList.get(pos).getNombre() );
                getActivity().startActivity(intent);
            }
        });

    }

    /**
     * The Class UserAdapter is the adapter class for User ListView. This
     * adapter shows the user name and it's only online status for each item.
     */
    private class UserAdapter extends BaseAdapter {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            return mList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public MascotaDTO getItem(int arg0) {
            return mList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            if (v == null)
                v = inflater.inflate(R.layout.chat_item_lista, null);

            MascotaDTO c = getItem(pos);
            //TextView lbl = (TextView) v;
            //LinearLayout lbl = (LinearLayout) v;

            TextView lbl = (TextView) v.findViewById(R.id.TextViewChat);
            CircularImageView circularImageView=(CircularImageView) v.findViewById(R.id.circularImagePerfilMascotaChat);
            lbl.setText(c.getNombre());
            lbl.setTextColor(Color.BLACK);
            setImagen(c.getIdFoto(), circularImageView);
            /*lbl.setCompoundDrawablesWithIntrinsicBounds(
                    c.getBoolean("online") ? R.drawable.ic_online
                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);*/

            return v;
        }

    }
}
