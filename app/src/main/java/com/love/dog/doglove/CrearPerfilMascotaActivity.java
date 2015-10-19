package com.love.dog.doglove;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.love.dog.doglove.DTO.ListaPerrosDTO;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.love.dog.doglove.presenter.IRegistroMascotaPresenter;
import com.love.dog.doglove.presenter.IRegistroPresenter;
import com.love.dog.doglove.presenter.RegistroMascotaPresenter;
import com.love.dog.doglove.presenter.RegistroPresenter;
import com.love.dog.doglove.view.GenericView;
import com.love.dog.doglove.view.RegistroMascotaView;
import com.love.dog.doglove.view.RegistroView;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.pkmmte.view.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrearPerfilMascotaActivity extends AppCompatActivity implements View.OnClickListener,RegistroMascotaView {

    EditText editMascota,editEdad;
    Spinner spinRazas;
    String raza,idDueno;
    ImageButton butCrearPerfil;
    private static final int  SELECT_FILE=2;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private File photoFile = null;
    private CircularImageView imagenPerfilMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_mascota);

        editEdad=(EditText)findViewById(R.id.editEdadMascota);
        editMascota=(EditText)findViewById(R.id.editMascota);
        spinRazas=(Spinner)findViewById(R.id.spinnerRaza);
        Intent intent = getIntent();
        idDueno= intent.getIntExtra("id",0)+ "";
        System.out.println(idDueno);

        butCrearPerfil=(ImageButton) findViewById(R.id.imageButtonCrearCuentaMascota);
        butCrearPerfil.setOnClickListener(this);

        imagenPerfilMascota=(CircularImageView) findViewById(R.id.circularImageMascota);
        imagenPerfilMascota.setOnClickListener(this);
        /**PARA PRUEBA*

        intent.setClass(this, SwipeActivity.class);
        this.startActivity(intent);**/
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButtonCrearCuentaMascota) {
           String nombre = editMascota.getText().toString(); //obtener info, lo ideal es tener una clase q vea logica de ogin,
            raza=String.valueOf(spinRazas.getSelectedItem());
            String edad = editEdad.getText().toString();
            IRegistroMascotaPresenter presenter = new RegistroMascotaPresenter(this);
            presenter.registrar(nombre,raza,edad,idDueno);
            Log.w("mmya ", nombre + "111111");
        }else {
            selectImage();
        }

    }


    @Override
    public void onRegistroCorrecto(List<MascotaDTO> perros) {
        Intent intent = new Intent();
        intent.setClass(this, SwipeActivity.class);
        //System.out.println("CPMA: "+perros.get(1).getNombre());
        ListaPerrosDTO listaPerrosDTO=new ListaPerrosDTO(perros);
        intent.putExtra("perros",listaPerrosDTO);
        this.startActivity(intent);
        //getSerializableextra
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

    //////////////////////////FOTO
    private void subirFoto(Bitmap foto){
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
        final ParseObject imgupload = new ParseObject("ImagenMascota");
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
                    String objectId = imgupload.getObjectId();
                    System.out.println("ID FOTO MASCOTA: "+objectId);
                } else {
                    // Failure!
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_TAKE_PHOTO){
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
                imagenPerfilMascota.setImageBitmap(bitmap);
                subirFoto(bitmap);
            }
        }else if(requestCode==SELECT_FILE){
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                imagenPerfilMascota.setImageURI(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    subirFoto(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       /* switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
                    imagenPerfilMascota.setImageBitmap(bitmap);
                }
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    imagenPerfilMascota.setImageURI(selectedImageUri);

                }

        }*/
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearPerfilMascotaActivity.this);
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
                Log.w("FOTO ", "LLEGO ACA2");

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




}
