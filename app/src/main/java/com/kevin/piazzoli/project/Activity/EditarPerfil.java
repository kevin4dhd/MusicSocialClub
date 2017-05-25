package com.kevin.piazzoli.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.kevin.piazzoli.project.Activity.Principal.ActivityPrincipal;
import com.kevin.piazzoli.project.Preferences;
import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;
import com.kevin.piazzoli.project.SQLite.UsuarioClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by user on 22/05/2017.
 */

public class EditarPerfil extends AppCompatActivity {

    private OperacionesBaseDatos datos;

    private Spinner spCountry;
    private Button aceptar,cancelar;

    private UsuarioClass usuarioClass;
    private TextInputLayout nombre, apellidos, song,instrumento;
    private RadioButton rdHombre;
    private RadioButton rdMujer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        setTitle("Registro");

        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());
        usuarioClass = new UsuarioClass(datos.obtenerUsuarioPorId(Preferences.obtenerPreferenceString(this,Preferences.PREFERENCE_USUARIO_LOGIN)));
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        nombre = (TextInputLayout) findViewById(R.id.nombreRegistro);
        apellidos = (TextInputLayout) findViewById(R.id.apellidosRegistro);
        song = (TextInputLayout) findViewById(R.id.preguntaSecreta);
        instrumento = (TextInputLayout) findViewById(R.id.preguntaOpcional);

        rdHombre = (RadioButton) findViewById(R.id.RDhombre);
        rdMujer = (RadioButton) findViewById(R.id.RDmujer);

        rdHombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdMujer.setChecked(false);
            }
        });

        rdMujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHombre.setChecked(false);
            }
        });

        spCountry = (Spinner)findViewById(R.id.spinnerPaises);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        spCountry.setAdapter(adapter);
        spCountry.setSelection(adapter.getPosition(usuarioClass.getCountry()));

        aceptar = (Button) findViewById(R.id.buttonAceptar);
        cancelar = (Button) findViewById(R.id.buttonCancelar);

        rdHombre.setChecked(usuarioClass.getSex().equalsIgnoreCase("hombre"));
        rdMujer.setChecked(!usuarioClass.getSex().equalsIgnoreCase("hombre"));

        nombre.getEditText().setText(usuarioClass.getNombre());
        apellidos.getEditText().setText(usuarioClass.getApellido());
        song.getEditText().setText(usuarioClass.getMusic());
        instrumento.getEditText().setText(usuarioClass.getInstrument().isEmpty() ? "No tienes ningun instrumento" : usuarioClass.getInstrument());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarLogin();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreText = nombre.getEditText().getText().toString();
                String apellidoText = apellidos.getEditText().getText().toString();
                String songText = song.getEditText().getText().toString();
                String instrumentoText = instrumento.getEditText().getText().toString();
                boolean va = validarTexto(nombre,nombreText)
                        && validarTexto(apellidos,apellidoText)
                        && validarTexto(song,songText);
                if(va){
                    verificarRegistro(
                            nombreText,
                            apellidoText,
                            songText,
                            instrumentoText,
                            spCountry.getSelectedItem().toString().trim(),
                            rdHombre.isChecked() ? "hombre" : "mujer".trim(),
                            usuarioClass);
                }

            }
        });

    }

    public void verificarRegistro(String nombre, String apellido, String music,String instrument, String country, String sex,UsuarioClass userObjeto){
        if(!nombre.isEmpty() && !apellido.isEmpty() && !music.isEmpty() && !country.isEmpty() && !sex.isEmpty()) {
            UsuarioClass usuarioClass = new UsuarioClass();
            usuarioClass.setIdGenerate(userObjeto.getIdGenerate());
            usuarioClass.setIdUser(userObjeto.getIdUser());
            usuarioClass.setNombre(nombre);
            usuarioClass.setApellido(apellido);
            usuarioClass.setMusic(music);
            usuarioClass.setInstrument(instrument);
            usuarioClass.setCountry(country);
            usuarioClass.setSex(sex);
            usuarioClass.setPassword(userObjeto.getPassword());
            datos.actualizarUsuario(usuarioClass);
            Toast.makeText(EditarPerfil.this," se actualizaron los datos correctamente", Toast.LENGTH_SHORT).show();
            regresarLogin();
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
