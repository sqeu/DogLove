package com.love.dog.doglove;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ContenedorActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigation;
    DrawerLayout dlaContenedor;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_prueba);

        navigation = (NavigationView)findViewById(R.id.navigation);
        dlaContenedor = (DrawerLayout) findViewById(R.id.dlaContenedor);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlaContenedor.openDrawer(GravityCompat.START);
            }
        });

        Fragment swipeFragment = SwipeFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.flaContenido, swipeFragment);
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
                Fragment miPerifl =
                        MiPerfilFragment.newInstance();

                ft.replace(R.id.flaContenido, miPerifl);
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