package com.love.dog.doglove;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton butLogin, butLogFace,butRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ytpDHP4GVEpFwCWw68StrCrIrhJS05OOo5HToBza", "5AwnfJyEhJapFQ4S6Iyq8fXZbVrVPeqYuFsXyUTK");

        butLogin=(ImageButton) findViewById(R.id.imageButtonLogin);
        butLogin.setOnClickListener(this);
        butLogFace=(ImageButton) findViewById(R.id.imageButtonLoginFB);
        butLogFace.setOnClickListener(this);
        butRegistro=(ImageButton) findViewById(R.id.imageButtonRegistro);
        butRegistro.setOnClickListener(this);
    }

    public void onClick(View view) {
        //logica para diferenciar de q botn es y de acuerdo q hacer
        if(view.getId()==R.id.imageButtonRegistro){
                //ir activity de perfil
                Intent intent=new Intent();//objeto o clase q se encarga de lamar a otros componentes de android(activity q estoy, donde quiero ir)
                intent.setClass(InicioActivity.this, CrearCuentaActivity.class);
                startActivity(intent);
        }else if(view.getId() == R.id.imageButtonLoginFB){
            Log.i("MainActivity","se hizo click en el botnon LoginFB");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();
        }else{
            Log.i("MainActivity","se hizo click en el botnon ingreso");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();
        }
        //Log.i("MainActivity","se hizo click en el botnon Login");//SE PONE nombre de lo q se quiere ejecutar
        //Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();  //cada vez q te pide contexto es el activity as i q this
    }

}
