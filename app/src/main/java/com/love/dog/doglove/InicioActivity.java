package com.love.dog.doglove;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.love.dog.doglove.parse.Message;
import com.parse.Parse;
import com.parse.ParseObject;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton butLogin, butRegistro;
    LoginButton butLogFace;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, "ytpDHP4GVEpFwCWw68StrCrIrhJS05OOo5HToBza", "5AwnfJyEhJapFQ4S6Iyq8fXZbVrVPeqYuFsXyUTK");

        butLogin=(ImageButton) findViewById(R.id.imageButtonLogin);
        butLogin.setOnClickListener(this);
        butRegistro=(ImageButton) findViewById(R.id.imageButtonRegistro);
        butRegistro.setOnClickListener(this);

        //FACEBOOK
        butLogFace=(LoginButton) findViewById(R.id.imageButtonLoginFB);
        butLogFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println( "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());
/*                10-17 17:10:20.436    7157-7157/com.love.dog.doglove I/System.out﹕ User ID: 531329513712145
                10-17 17:10:20.436    7157-7157/com.love.dog.doglove I/System.out﹕ Auth Token: CAANoyIaUPoUBAEX5iDngXvDwIwgMGWgkxdY0GBmMvAqJZChpCzDGO90XyhW5OZCeZB0zvFrb1bbck7ZAjSZALbadeTESAQt2m37Mjh28KKa6I2C6LdnPX3OpLAqlGZBsZBWjTIReJ8CdeHePQlkZCZAW5OIKHMTuJ1SP9TZCC4116CVQso7pZA6YsqZBVscPU66rZBe4ZD
*/
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                System.out.println(e);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View view) {
        //logica para diferenciar de q botn es y de acuerdo q hacer
        if(view.getId()==R.id.imageButtonRegistro){
                //ir activity de perfil
                Intent intent=new Intent();//objeto o clase q se encarga de lamar a otros componentes de android(activity q estoy, donde quiero ir)
                intent.setClass(InicioActivity.this, ChatActivity.class);
                startActivity(intent);

        }else{
            Log.i("MainActivity", "se hizo click en el botnon ingreso");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();
        }
       /* else if(view.getId() == R.id.imageButtonLoginFB){
            Log.i("MainActivity","se hizo click en el botnon LoginFB");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();*/
        //Log.i("MainActivity","se hizo click en el botnon Login");//SE PONE nombre de lo q se quiere ejecutar
        //Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();  //cada vez q te pide contexto es el activity as i q this
    }

}
