package com.love.dog.doglove;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.DTO.UsuarioDTO;
import com.love.dog.doglove.presenter.IObtenerClientePresenter;
import com.love.dog.doglove.presenter.IObtenerPerroPresenter;
import com.love.dog.doglove.presenter.ObtenerClientePresenter;
import com.love.dog.doglove.presenter.ObtenerPerroPresenter;
import com.love.dog.doglove.view.ObtenerClienteView;
import com.love.dog.doglove.view.ObtenerMascotaView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pkmmte.view.CircularImageView;

/**
 * Created by Hugo on 10/24/2015.
 */
public class MiPerfilFragment extends Fragment implements ObtenerMascotaView,ObtenerClienteView {

    String idDueno,idPerro;
    TextView nombre,nombreMascota,edadMascota,correo;
    CircularImageView perfilDueno,miPerro;

    public static MiPerfilFragment newInstance(){
        return new MiPerfilFragment();
    }

    public MiPerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent=getActivity().getIntent();
        idDueno= intent.getStringExtra("id");//ponerlo en idGoogle
        idPerro=intent.getStringExtra("idPerro");
        System.out.println("perfil,idDueno" + idDueno);
        System.out.println("perfil fragment, id perro: "+ idPerro);

        nombre=(TextView)getView().findViewById(R.id.textViewNombrePerfil);
        correo=(TextView) getView().findViewById(R.id.textViewEmailPerfil);
        edadMascota=(TextView)getView().findViewById(R.id.textViewEdadPerfil);
        nombreMascota=(TextView)getView().findViewById(R.id.textViewNombreMascotaPerfil);

        perfilDueno=(CircularImageView) getView().findViewById(R.id.circularImagePerfil);
        miPerro=(CircularImageView) getView().findViewById(R.id.circularImagePerfilMascota);
        IObtenerClientePresenter obtenerClientePresenter= new ObtenerClientePresenter(this);
        obtenerClientePresenter.obtenerCliente(idDueno);

        IObtenerPerroPresenter obtenerPerroPresenter=new ObtenerPerroPresenter(this);
        obtenerPerroPresenter.obtenerPerro(idPerro);

    }

    public void setImagen(String idFoto,  final CircularImageView imagen, String tipoImagen){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                tipoImagen);

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
                                            imagen.setImageBitmap(bmp);
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

    @Override
    public void onObtenerCorrecto(UsuarioDTO usuarioDTO) {
        String usuario="ImagenDueno";
        nombre.setText("Nombre:  "+usuarioDTO.getNombre());
        correo.setText("Correo:  "+usuarioDTO.getCorreo());

        System.out.println("cliente idfoto" + usuarioDTO.getIdFoto());

        setImagen(usuarioDTO.getIdFoto(), perfilDueno,usuario);
    }

    @Override
    public void onObtenerCorrecto(MascotaDTO mascotaDTO) {
        String mascota="ImagenMascota";
        edadMascota.setText("Edad:  "+mascotaDTO.getEdad());
        nombreMascota.setText("Nombre:  "+mascotaDTO.getNombre());
        System.out.println("masco idfoto" + mascotaDTO.getIdFoto());

        setImagen(mascotaDTO.getIdFoto(),miPerro,mascota);
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
}
