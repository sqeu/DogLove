package com.love.dog.doglove;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.love.dog.doglove.DTO.ListaPerrosDTO;
import com.love.dog.doglove.DTO.MascotaDTO;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 10/24/2015.
 */
public class SwipeFragment extends Fragment {
    CardContainer mCardContainer;
    int posicion = 0;
    private ProgressDialog progressDialog;
    CardModel cardModel;

    public static SwipeFragment newInstance() {
        return new SwipeFragment();
    }

    public SwipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_swipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCardContainer = (CardContainer) getView().findViewById(R.id.ccPerro);

        ListaPerrosDTO listaPerrosDTO = (ListaPerrosDTO) getActivity().getIntent().getSerializableExtra("perros");
        List<MascotaDTO> perros = new ArrayList<>(listaPerrosDTO.getPerros());

        final SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity().getApplicationContext());
        //ContextCompat.getDrawable(getView().getContext(), R.drawable.picture1)
        for (final MascotaDTO perro : perros) {
//            System.out.println(bmp.getByteCount());


            //  progressDialog = ProgressDialog.show(getActivity(), "", "Downloading Image...", true);
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "ImagenMascota");

            // Locate the objectId from the class
            query.getInBackground(perro.getIdFoto(),
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
                                                //imagenPerfil.setImageBitmap(bmp);
                                                cardModel = new CardModel(perro.getNombre() + ", " + perro.getEdad(), "aca le puedo poner id", bmp, perro.getIdcliente());

                                                // cardModel.setCardImageDrawable( new BitmapDrawable(getResources(), bmp));
                                                cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {

                                                    @Override
                                                    public void onLike() {
                                                        Log.i("Swipeable Cards", "I liked the card");

                                                    }

                                                    @Override
                                                    public void onDislike() {
                                                        Log.i("Swipeable Cards", "I disliked the card");
                                                    }
                                                });

                                                adapter.add(cardModel);

                                                mCardContainer.setAdapter(adapter);
                                                //posicion=adapter.getCount()-1;

                                                ((ImageView) getView().findViewById(com.andtinder.R.id.like)).setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        // TODO Auto-generated method stub
                                                        //System.out.println("titulo probable: " + adapter.pop().getTitle());
                                                        System.out.println(adapter.getCardModel(posicion).getIdCliente() + "la mascota " + adapter.getCardModel(posicion).getTitle());

                                                        //System.out.println("hay tantos items en adapter " + posicion);

                                                        // System.out.println("hice like a : " + adapter.getCardModel(adapter.getCount()));

                                                        mCardContainer.quitarVista();
                                                        posicion++;

                                                        imagenGustada();
                                                    }
                                                });

                                                ((ImageView) getView().findViewById(com.andtinder.R.id.dislike)).setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        // TODO Auto-generated method stub

                                                    }
                                                });

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
        // adapter.add(new CardModel("Title1", "Description goes here", ContextCompat.getDrawable(this, R.drawable.picture1)));



    }


    public void imagenGustada() {

    }
}
