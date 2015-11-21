package com.love.dog.doglove;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
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

import java.io.IOException;

public class MatchPatitaActivity extends Activity implements View.OnClickListener{
    Button btnIniciarChat;
    Button btnSeguirBuscando;

    String idDueno1, idMascota1, idDuenoPareja, idMascotaPareja,idChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_patita);

        Intent intent = getIntent();
        idDueno1 = intent.getStringExtra("iddueno1");
        idMascota1 = intent.getStringExtra("idmascota1");
        idDuenoPareja = intent.getStringExtra("iddueno2");
        idMascotaPareja = intent.getStringExtra("idmascota2");
        idChat=intent.getStringExtra("idchat");
        //System.out.println("en match patita"+idChat);

        btnIniciarChat=(Button) findViewById(R.id.buttonIniciarChat);
        btnIniciarChat.setOnClickListener(this);
        btnSeguirBuscando=(Button) findViewById(R.id.buttonSeguirBuscando);
        btnSeguirBuscando.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonIniciarChat){
            //enviarle el idchat
            Intent intent = new Intent();
            intent.setClass(this, ContenedorActivity.class);
            intent.putExtra("idchat", idChat);
            intent.putExtra("id",idDueno1);
            intent.putExtra("idmascota1",idMascota1);
            this.startActivity(intent);

        }
    }
}
