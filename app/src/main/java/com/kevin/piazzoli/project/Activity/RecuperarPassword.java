package com.kevin.piazzoli.project.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kevin.piazzoli.project.Fragments.Recuperacion.ComunicadorChangePassword;
import com.kevin.piazzoli.project.Fragments.Recuperacion.fragment_recuperacion_password;
import com.kevin.piazzoli.project.R;

/**
 * Created by user on 21/05/2017.
 */

public class RecuperarPassword extends AppCompatActivity implements ComunicadorChangePassword {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);
        Fragment fragment = new fragment_recuperacion_password();
        FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
        frag.replace(R.id.contenedor, fragment).commit();
        setTitle("Recuperar Contraseña");
    }

    @Override
    public void changeFragment(Fragment fragment) {
        FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
        frag.setCustomAnimations(R.anim.derecha_in, R.anim.derecha_out, R.anim.derecha_in, R.anim.derecha_out);
        frag.replace(R.id.contenedor, fragment).commit();
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
}
