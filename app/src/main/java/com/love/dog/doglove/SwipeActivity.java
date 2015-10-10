package com.love.dog.doglove;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.love.dog.doglove.DTO.ListaPerrosDTO;
import com.love.dog.doglove.DTO.MascotaDTO;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {
    CardContainer mCardContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        mCardContainer = (CardContainer) findViewById(R.id.ccPerro);

        ListaPerrosDTO perros;

        Intent intent= new Intent();
        try {
            perros = (ListaPerrosDTO) intent.getSerializableExtra("perros");
            List<MascotaDTO> listaPerros=new ArrayList<>(perros.getPerros());

            for (MascotaDTO perro : listaPerros) {
                System.out.println(perro.getNombre());
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        final SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getApplicationContext());

        adapter.add(new CardModel("Title1", "Description goes here", ContextCompat.getDrawable(this, R.drawable.picture1)));
        adapter.add(new CardModel("Title2", "Description goes here", ContextCompat.getDrawable(this, R.drawable.prueba)));

        mCardContainer.setAdapter(adapter);
        ((ImageView) findViewById(com.andtinder.R.id.like)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.w("hizo click", "clickkk");
                mCardContainer.quitarVista();
            }
        });

        ((ImageView) findViewById(com.andtinder.R.id.dislike)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.w("hizo click", "clickkk dislike");

            }
        });

    }


}
