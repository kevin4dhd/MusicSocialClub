package com.kevin.piazzoli.project.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.piazzoli.project.Activity.Principal.ActivityPrincipal;
import com.kevin.piazzoli.project.Preferences;
import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;
import com.kevin.piazzoli.project.SQLite.UsuarioClass;

/**
 * Created by user on 24/05/2017.
 */

public class CambiarPassword extends AppCompatActivity {

    private TextInputLayout song;
    private TextInputLayout password, passwordRepeat;
    private Button cambiar;
    private OperacionesBaseDatos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        datos = OperacionesBaseDatos.obtenerInstancia(this);

        song = (TextInputLayout) findViewById(R.id.songRecuperar);
        cambiar = (Button) findViewById(R.id.buttonCambiar);
        password = (TextInputLayout) findViewById(R.id.password);
        passwordRepeat = (TextInputLayout) findViewById(R.id.repetirPassword);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                String music = song.getEditText().getText().toString();
                boolean b = validarTexto(song,music);

                if (b) {
                    Cursor c = datos.obtenerUsuarioPorId(Preferences.obtenerPreferenceString(CambiarPassword.this,Preferences.PREFERENCE_USUARIO_LOGIN));
                    if(c.getCount() > 0){
                        UsuarioClass usuarioClass = new UsuarioClass(c);
                        if(usuarioClass.getMusic().equals(music)){
                            String passwordText = password.getEditText().getText().toString();
                            String passwordRepeatText = passwordRepeat.getEditText().getText().toString();

                            boolean va = validarTexto(password,passwordText) && validarTexto(passwordRepeat,passwordRepeatText);
                            if(va){
                                if(validarPassword(passwordRepeat,passwordText,passwordRepeatText)){
                                    usuarioClass.setPassword(passwordText);
                                    datos.actualizarUsuario(usuarioClass);
                                    Toast.makeText(CambiarPassword.this, "La contraseña se cambio correctamente", Toast.LENGTH_SHORT).show();
                                    regresarLogin();
                                }
                            }
                        }else{
                            Toast.makeText(CambiarPassword.this, "Respuesta secreta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CambiarPassword.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean validarTexto(TextInputLayout e,String s){
        if ((s.trim()).isEmpty()) {
            e.setError("campo invalido");
            return false;
        } else{
            e.setError(null);
            return true;
        }
    }

    private boolean validarPassword(TextInputLayout pR,String s,String sR){
        if (!s.equals(sR)) {
            pR.setError("Las contraseñas deben ser iguales");
            return false;
        } else{
            pR.setError(null);
            return true;
        }
    }

    private void regresarLogin(){
        finish();
        Intent intent = new Intent(this,ActivityPrincipal.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        regresarLogin();
    }

}
