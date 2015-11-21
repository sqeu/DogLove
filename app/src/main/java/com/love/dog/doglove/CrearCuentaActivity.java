package com.love.dog.doglove;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.love.dog.doglove.gcm.GCMClientManager;
import com.love.dog.doglove.gps.GPSTracker;
import com.love.dog.doglove.presenter.IRegistroPresenter;
import com.love.dog.doglove.presenter.RegistroPresenter;
import com.love.dog.doglove.view.RegistroView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.pkmmte.view.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

//AppCompatActivity
public class CrearCuentaActivity extends Activity implements View.OnClickListener, RegistroView {
    ImageButton butCrearCuenta;
    CircularImageView imagenPerfil;
    EditText editNombre, editPass, editEmail, editApellido;
    private static final int SELECT_FILE = 2;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private File photoFile = null;
    GPSTracker gps;
    private ProgressDialog progressDialog;
    String idFoto=null;
    double latitude;
    double longitude;

    String PROJECT_NUMBER = "139161743842";

    String idGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        butCrearCuenta = (ImageButton) findViewById(R.id.imageButtonCrearCuenta);
        butCrearCuenta.setOnClickListener(this);

        editNombre = (EditText) findViewById(R.id.editUsername);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editPass = (EditText) findViewById(R.id.editTextPass);
        editEmail = (EditText) findViewById(R.id.editEmail);

        imagenPerfil = (CircularImageView) findViewById(R.id.circularImagePerfil);
        imagenPerfil.setOnClickListener(this);

        //gps
        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(
                    getApplicationContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
        // circularImageView.setImageResource(R.drawable.logo);

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.d("Registration id", registrationId);
                idGoogle=registrationId;
                //send this registrationId to your server
                //verificar q notifiacion abra matchactivity pero no abra el registro
                //poner esto en registro y guardarlo en BD, crear campo paara guardarlo
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });


/* OBTENER FOTOS PARA UN IMAGEVIEW
        //9KSJIHrjo2
        progressDialog = ProgressDialog.show(this, "",
                "Downloading Image...", true);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "ImagenDueno");

        // Locate the objectId from the class
        query.getInBackground("9KSJIHrjo2",
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

                                            imagenPerfil.setImageBitmap(bmp);

                                            // Close progress dialog
                                            progressDialog.dismiss();

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }


                                });
                    }


                });

*/
    }

    public void onClick(View view) {
        //logica para diferenciar de q botn es y de acuerdo q hacer
        if (view.getId() == R.id.imageButtonCrearCuenta) {
            String nombre = editNombre.getText().toString(); //obtener info, lo ideal es tener una clase q vea logica de ogin,
            String pass = editPass.getText().toString();
            String email = editEmail.getText().toString();
            String apellido = editApellido.getText().toString();

            /* pRUEBA =
            onRegistroCorrecto();**/

            if(idFoto!=null){
                IRegistroPresenter presenter = new RegistroPresenter(this);
                presenter.registrar(email, pass, nombre, apellido, idFoto, Double.toString(latitude), Double.toString(longitude),idGoogle);
            }else{
                Toast.makeText(this, "Escoja una foto",Toast.LENGTH_LONG).show();
            }
        } else {
            selectImage();
        }


        //Log.i("MainActivity","se hizo click en el botnon Login");//SE PONE nombre de lo q se quiere ejecutar
        //Toast.makeText(this, "opcion login...proximamente",Toast.LENGTH_LONG).show();  //cada vez q te pide contexto es el activity as i q this
    }


    @Override
    public void onRegistroCorrecto(int id) {
        Intent intent = new Intent();
        intent.setClass(this, CrearPerfilMascotaActivity.class);
        intent.putExtra("id", id);
        this.startActivity(intent);

    }

    public ApplicationController getApplicationController() {
        return (ApplicationController) getApplication();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(
                this,
                "EXC: " + msg,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
///////////////////////FOTOS


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearCuentaActivity.this);
        builder.setTitle("Usa una foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    dispatchTakePictureIntent();
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private File dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.w("CAMARA", "error file");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

        return photoFile;
    }

    private File createImageFile() throws IOException {
        String mCurrentPhotoPath;
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "DOG_LOVE_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
                imagenPerfil.setImageBitmap(bitmap);
                subirFoto(bitmap);
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                imagenPerfil.setImageURI(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    subirFoto(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void subirFoto(Bitmap foto) {
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
                    idFoto=objectId.toString();
                    //9KSJIHrjo2
                } else {
                    System.out.println("ALGO FALLO, SUBIEDNO IMAGEN");
                }

            }
        });

    }
        /*switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
                    imagenPerfil.setImageBitmap(bitmap);
                    subirFoto(bitmap);
                }
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    imagenPerfil.setImageURI(selectedImageUri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        subirFoto(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }*/

}
