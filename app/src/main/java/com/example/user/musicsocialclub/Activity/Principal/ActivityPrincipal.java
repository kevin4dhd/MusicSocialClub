package com.example.user.musicsocialclub.Activity.Principal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.musicsocialclub.Activity.RecuperarPassword;
import com.example.user.musicsocialclub.Activity.Registro;
import com.example.user.musicsocialclub.Fragments.Principal.Post.PostAdapter;
import com.example.user.musicsocialclub.Fragments.Principal.ComunicadorPrincipal;
import com.example.user.musicsocialclub.Fragments.Principal.UploadAudio.FragmentUploadAudio;
import com.example.user.musicsocialclub.Preferences;
import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.UsuarioClass;

/**
 * Created by user on 20/05/2017.
 */

public class ActivityPrincipal extends AppCompatActivity implements ComunicadorPrincipal{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static PostAdapter postAdapter;
    private TextView textView;
    private OperacionesBaseDatos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutUsuarios);
        viewPager = (ViewPager) findViewById(R.id.viewPagerUsuarios);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterPrincipal(getSupportFragmentManager()));
        textView = (TextView) findViewById(R.id.usuarioName);
        datos = OperacionesBaseDatos.obtenerInstancia(this);
        Cursor c = datos.obtenerUsuarioPorId(Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN));
        UsuarioClass usuarioClass = new UsuarioClass(c);
        textView.setText(usuarioClass.getNombre()+" "+usuarioClass.getApellido());
    }

    @Override
    public void actualizarPost() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void finalizar() {
        finish();
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void actualizarFragmentAudio() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPagerUsuarios + ":" + viewPager.getCurrentItem());
        ((FragmentUploadAudio)page).actualizar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.editarPerfil:
                startActivity(new Intent(this,Registro.class));
                break;
            case R.id.cambiarPassword:
                startActivity(new Intent(this,RecuperarPassword.class));
                break;
            case R.id.cerrarSesion:
                Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
                break;
        }

        if(id==R.id.opciones){
            Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
