package com.kevin.piazzoli.project.Activity.Principal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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

import com.kevin.piazzoli.project.Activity.CambiarPassword;
import com.kevin.piazzoli.project.Activity.EditarPerfil;
import com.kevin.piazzoli.project.Activity.Login;
import com.kevin.piazzoli.project.Fragments.Principal.Post.PostAdapter;
import com.kevin.piazzoli.project.Fragments.Principal.ComunicadorPrincipal;
import com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.FragmentUploadAudio;
import com.kevin.piazzoli.project.Preferences;
import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;
import com.kevin.piazzoli.project.SQLite.UsuarioClass;

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
                Intent intent = new Intent(this,EditarPerfil.class);
                startActivity(intent);
                break;
            case R.id.cambiarPassword:
                startActivity(new Intent(this,CambiarPassword.class));
                break;
            case R.id.cerrarSesion:
                Preferences.savePreferenceBoolean(this,false,Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                Intent i = new Intent(this,Login.class);
                startActivity(i);
                finish();
                break;
            case R.id.codigoFuente:
                Uri uri = Uri.parse("https://github.com/kevin4dhd/MusicSocialClub");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
        }

        if(id==R.id.opciones){
            Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
