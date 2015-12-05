package com.love.dog.doglove;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.love.dog.doglove.DTO.ListaPerrosDTO;
import com.love.dog.doglove.DTO.MascotaDTO;

import java.util.HashSet;
import java.util.Set;


public class ContenedorActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigation;
    DrawerLayout dlaContenedor;
    Toolbar toolbar;
    String idDueno;
    String idPerro,idChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_prueba);

        navigation = (NavigationView)findViewById(R.id.navigation);
        dlaContenedor = (DrawerLayout) findViewById(R.id.dlaContenedor);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.footprint);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlaContenedor.openDrawer(GravityCompat.START);
            }
        });


       // ft.commit();

        FragmentTransaction ft;
        Intent intent = getIntent();
        idDueno= intent.getStringExtra("id");//funciona
        idPerro=intent.getStringExtra("idPerro");
        idChat=intent.getStringExtra("idchat");
        System.out.println("contenedor: "+idChat + ":: Conteneedor:idPerro: "+idPerro + "IDDUENO:"+idDueno);
        if(idChat!=null){
            Fragment chatFragment = ChatFragment.newInstance();
            ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.flaContenido, chatFragment);
        }else{
            Fragment swipeFragment = SwipeFragment.newInstance();
            ft= getFragmentManager().beginTransaction();
            ft.replace(R.id.flaContenido, swipeFragment);
        }
        ft.commit();
        navigation.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contenedor, menu);
        return true;
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

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment ;
        switch(menuItem.getItemId()){
            case R.id.menPerfil:
                fragment =
                        MiPerfilFragment.newInstance();

                ft.replace(R.id.flaContenido, fragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
            case R.id.menConfiguracion:
                fragment =
                        ConfiguracionFragment.newInstance();

                ft.replace(R.id.flaContenido, fragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
            case R.id.menSwipe:
                Fragment swipeFragment =
                        SwipeFragment.newInstance();

                ft.replace(R.id.flaContenido, swipeFragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
            case R.id.menHistorial:
                fragment =
                        HistorialFragment.newInstance();

                ft.replace(R.id.flaContenido, fragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
            case R.id.menPrefDeBusqueda:
                fragment =
                        PrefBusquedaFragment.newInstance();

                ft.replace(R.id.flaContenido, fragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
            case R.id.menServicios:
                fragment =
                        ServiciosFragment.newInstance();

                ft.replace(R.id.flaContenido, fragment);
                ft.commit();
                dlaContenedor.closeDrawers();
                return true;
        }

        return false;
    }
}