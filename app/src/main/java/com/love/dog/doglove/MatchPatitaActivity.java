package com.love.dog.doglove;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.love.dog.doglove.gcm.GCMClientManager;
import com.love.dog.doglove.view.ObtenerMensajesView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pkmmte.view.CircularImageView;

import java.io.IOException;

public class MatchPatitaActivity extends Activity implements View.OnClickListener{
    Button btnIniciarChat;
    Button btnSeguirBuscando;
    CircularImageView miPerro,otroPerro;
    String idDueno1, idMascota1, idDuenoPareja, idMascotaPareja,idChat,idFoto1,idFoto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_patita);

        Intent intent = getIntent();
        idDueno1 = intent.getStringExtra("iddueno1");
        idMascota1 = intent.getStringExtra("idmascota1");
        idFoto1=intent.getStringExtra("idfoto1");
        idDuenoPareja = intent.getStringExtra("iddueno2");
        idMascotaPareja = intent.getStringExtra("idmascota2");
        idFoto2=intent.getStringExtra("idfoto2");
        idChat=intent.getStringExtra("idchat");
        //System.out.println("en match patita"+idChat);

        btnIniciarChat=(Button) findViewById(R.id.buttonIniciarChat);
        btnIniciarChat.setOnClickListener(this);
        btnSeguirBuscando=(Button) findViewById(R.id.buttonSeguirBuscando);
        btnSeguirBuscando.setOnClickListener(this);

        miPerro=(CircularImageView) findViewById(R.id.circularImageMiPerro);
        getImagen(idFoto1, miPerro);
        otroPerro=(CircularImageView) findViewById(R.id.circularImageOtroPerro);
        getImagen(idFoto2, otroPerro);

        SharedPreferences settings=getSharedPreferences("MiFoto",0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString("idfoto1", idFoto1);
        editor.commit();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonIniciarChat){
            //enviarle el idchat
            Intent intent = new Intent();
            intent.setClass(this, ContenedorActivity.class);
            intent.putExtra("idchat", idChat);
            intent.putExtra("id",idDueno1);
            intent.putExtra("idPerro",idMascota1);
            intent.putExtra("idfoto2",idFoto2);
            this.startActivity(intent);

        }else {
            Intent intent = new Intent();
            //intent.putExtra("perros",listaPerrosDTO);
            intent.putExtra("idPerro",idMascota1);
            intent.setClass(this, ContenedorActivity.class);
            this.startActivity(intent);

        }
    }

    public void getImagen(String idFoto, final CircularImageView circularImageView){
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
                                            circularImageView.setImageBitmap(bmp);
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
}




