package com.love.dog.doglove;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.love.dog.doglove.DTO.ListaPerrosDTO;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.gcm.GCMClientManager;
import com.love.dog.doglove.gps.GPSTracker;
import com.love.dog.doglove.parse.Message;
import com.love.dog.doglove.presenter.ILoginFBPresenter;
import com.love.dog.doglove.presenter.ILoginPresenter;
import com.love.dog.doglove.presenter.LoginFBPresenter;
import com.love.dog.doglove.presenter.LoginPresenter;
import com.love.dog.doglove.view.LoginFBView;
import com.love.dog.doglove.view.LoginView;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class InicioActivity extends Activity implements View.OnClickListener,LoginView,LoginFBView {
    ImageButton butLogin, butRegistro;
    LoginButton butLogFace;
    LocationManager mLocationManager;
    private CallbackManager callbackManager;
    String idFB=null;

    String idFoto=null;
    double latitude;
    double longitude;
    String idGoogle;
    String nombre;
    String PROJECT_NUMBER = "139161743842";

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, "ytpDHP4GVEpFwCWw68StrCrIrhJS05OOo5HToBza", "5AwnfJyEhJapFQ4S6Iyq8fXZbVrVPeqYuFsXyUTK");

        butLogin = (ImageButton) findViewById(R.id.imageButtonLogin);
        butLogin.setOnClickListener(this);
        butRegistro = (ImageButton) findViewById(R.id.imageButtonRegistro);
        butRegistro.setOnClickListener(this);

        ////////////////
        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            /*Toast.makeText(
                    getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();*/
        } else {
            gps.showSettingsAlert();
        }
        // circularImageView.setImageResource(R.drawable.logo);

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.d("Registration id", registrationId);
                idGoogle = registrationId;
                //send this registrationId to your server
                //verificar q notifiacion abra matchactivity pero no abra el registro
                //poner esto en registro y guardarlo en BD, crear campo paara guardarlo
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });




        ///////////////////////
        //FACEBOOK

        butLogFace = (LoginButton) findViewById(R.id.imageButtonLoginFB);
        butLogFace.setReadPermissions("public_profile email");
        butLogFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());
                //token me da nombre del usuario y foto
                //https://graph.facebook.com/me/picture?access_token=<your_access_token_here>
                //http://graph.facebook.com/67563683055/picture?type=large
                //id sera el correo, nombre, idfoto
                idFB = loginResult.getAccessToken().getUserId().toString();
                System.out.println(idFB);
                requestData();
                new LongOperation().execute(idFB);


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

    public void requestData(){
        System.out.println(AccessToken.getCurrentAccessToken().getToken());
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                System.out.println(json);
                try {
                    if(json != null){
                        nombre =json.getString("name");
                        System.out.println(nombre);
                        //"Profile link
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private class LongOperation extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String url="https://graph.facebook.com/" + params[0] + "/picture?type=large";
            Bitmap bitmap = null;
            try {
                //URL imgUrl = new URL();

                // Download Image from URL
                InputStream input = new java.net.URL(url).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
                subirFoto(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //subirFoto(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }


    }


  /*  public static Bitmap getBitmapFromURL(String src) {

        try {
            URL imgUrl = new URL(src);
            InputStream in = (InputStream) imgUrl.getContent();
            Bitmap  bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            System.out.println("error en descarga");
            return null;
        }
    }*/
    private void subirFoto(Bitmap foto) {
        final ILoginFBPresenter presenter = new LoginFBPresenter(this);

        Bitmap bitmap = foto;
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("androidbegin.png", image);
        // Upload the image into Parse Cloud
        file.saveInBackground();

        // Create a New Class called "ImageUpload" in Parse
        final ParseObject imgupload = new ParseObject("ImagenDueno");
        // Create a column named "ImageName" and set the string
        imgupload.put("ImageName", "FotoPerfil");

        // Create a column named "ImageFile" and insert the image
        imgupload.put("ImageFile", file);

        // Create the class and the columns
        imgupload.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Success!
                    Object objectId = imgupload.getObjectId();
                    System.out.println("ID FOTO: " + objectId);
                    idFoto = objectId.toString();
                    if(idFoto!=null){
                        System.out.println(nombre);
                        presenter.loginFB(idFB, nombre,null,idFoto, Double.toString(latitude), Double.toString(longitude),idGoogle);//CONSEGUIR IDFOTO PRIMERO
                    }
                    //9KSJIHrjo2
                } else {
                    System.out.println("ALGO FALLO, SUBIEDNO IMAGEN");
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick( View view) {
        //logica para diferenciar de q botn es y de acuerdo q hacer
        if(view.getId()==R.id.imageButtonRegistro){
                //ir activity de perfil
                Intent intent=new Intent();//objeto o clase q se encarga de lamar a otros componentes de android(activity q estoy, donde quiero ir)
                intent.setClass(InicioActivity.this, CrearCuentaActivity.class);
                startActivity(intent);

        }else{
            // Create Object of Dialog class
            final Dialog login = new Dialog(this);
            // Set GUI of login screen
            login.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            login.setContentView(R.layout.login_dialog);
            login.setTitle("Login to DogLove");

            // Init button of login GUI
            Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
            Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
            final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
            final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);
            final ILoginPresenter presenter = new LoginPresenter(this);

            // Attached listener for login GUI button
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0) {
                        // Validate Your login credential here than display message
                        presenter.login( txtUsername.getText().toString(), txtPassword.getText().toString());
                        /*Toast.makeText(InicioActivity.this,
                                "Login Sucessfull", Toast.LENGTH_LONG).show();*/

                        // Redirect to dashboard / home screen.
                        login.dismiss();
                    } else {
                        Toast.makeText(InicioActivity.this,
                                "Please enter Username and Password", Toast.LENGTH_LONG).show();

                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.dismiss();
                }
            });

            // Make dialog box visible.
            login.show();

        }
       /* else if(view.getId() == R.id.imageButtonLoginFB){
       Log.i("MainActivity", "se hizo click en el botnon ingreso");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();
            Log.i("MainActivity","se hizo click en el botnon LoginFB");
            Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();*/
        //Log.i("MainActivity","se hizo click en el botnon Login");//SE PONE nombre de lo q se quiere ejecutar
        //Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();  //cada vez q te pide contexto es el activity as i q this
    }



    @Override
    public void onLoginCorrecto(List<MascotaDTO> perros,String idPerro) {
        Intent intent = new Intent();
        intent.setClass(this, ContenedorActivity.class);
        ListaPerrosDTO listaPerrosDTO=new ListaPerrosDTO(perros);
        intent.putExtra("perros", listaPerrosDTO);
        intent.putExtra("idPerro",idPerro);
        this.startActivity(intent);
    }

    @Override
    public void onLoginFBCorrecto(List<MascotaDTO> perros,String idCliente) {
        Intent intent = new Intent();
        if(perros==null){
            intent.setClass(this, CrearPerfilMascotaActivity.class);
            intent.putExtra("id",Integer.parseInt(idCliente));
        }else{
            intent.setClass(this, ContenedorActivity.class);
            intent.putExtra("idPerro", idCliente);
            ListaPerrosDTO listaPerrosDTO=new ListaPerrosDTO(perros);
            intent.putExtra("perros", listaPerrosDTO);

        }
        this.startActivity(intent);
    }

    @Override
    public void onError(String msg) {
       /*if(msg.equals("Error")){

        }else if(msg.equals("ErrorFB")){
            //si el codigo no existe el backend lo guardara automaticamente
            //tendra q ccrear su perro
            Intent intent = new Intent();
            intent.setClass(this, ContenedorActivity.class);
            //intent.putExtra("id",  algo); deberia tener el id del cliente cuando se registre y
            this.startActivity(intent);
        }
        Toast.makeText(InicioActivity.this,
                "Error contrasena o usuario", Toast.LENGTH_LONG).show();*/
        Toast.makeText(
                this,
                "EXC: " + msg,
                Toast.LENGTH_LONG
        ).show();
        System.out.println("incorrecto");
    }

    @Override
    public ApplicationController getApplicationController() {
        return (ApplicationController) getApplication();
    }
}
