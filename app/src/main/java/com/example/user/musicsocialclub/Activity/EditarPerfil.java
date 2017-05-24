package com.example.user.musicsocialclub.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.UsuarioClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by user on 22/05/2017.
 */

public class EditarPerfil extends AppCompatActivity {

    private OperacionesBaseDatos datos;

    private Spinner spCountry;
    private Button button;

    private TextInputLayout user, nombre, apellidos, song, password, passwordRepeat,instrumento;
    private RadioButton rdHombre;
    private RadioButton rdMujer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registro");

        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());

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

        user = (TextInputLayout) findViewById(R.id.userRegister);
        nombre = (TextInputLayout) findViewById(R.id.nombreRegistro);
        apellidos = (TextInputLayout) findViewById(R.id.apellidosRegistro);
        song = (TextInputLayout) findViewById(R.id.preguntaSecreta);
        password = (TextInputLayout) findViewById(R.id.password);
        passwordRepeat = (TextInputLayout) findViewById(R.id.repetirPassword);
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
        spCountry.setSelection(adapter.getPosition("Mexico"));

        button = (Button) findViewById(R.id.buttonRegistro);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userText = user.getEditText().getText().toString();
                String nombreText = nombre.getEditText().getText().toString();
                String apellidoText = apellidos.getEditText().getText().toString();
                String songText = song.getEditText().getText().toString();
                String passwordText = password.getEditText().getText().toString();
                String passwordRepeatText = passwordRepeat.getEditText().getText().toString();
                String instrumentoText = instrumento.getEditText().getText().toString();
                boolean va = validarTexto(user,userText)
                        && validarTexto(nombre,nombreText)
                        && validarTexto(apellidos,apellidoText)
                        && validarTexto(song,songText)
                        && validarTexto(password,passwordText)
                        && validarTexto(passwordRepeat,passwordRepeatText);
                if(va){
                    if(validarPassword(passwordRepeat,passwordText,passwordRepeatText)){
                        if(verificarExistenciaUsuario(userText)){
                            verificarRegistro(
                                    userText,
                                    nombreText,
                                    apellidoText,
                                    songText,
                                    passwordText,
                                    instrumentoText,
                                    spCountry.getSelectedItem().toString().trim(),
                                    rdHombre.isChecked() ? "hombre" : "mujer".trim());
                        }else{
                            Toast.makeText(EditarPerfil.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

    }

    public void verificarRegistro(String user, String nombre, String apellido, String music, String password, String instrument, String country, String sex){
        if(!user.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !music.isEmpty() && !country.isEmpty() && !sex.isEmpty()) {
            UsuarioClass usuarioClass = new UsuarioClass();
            usuarioClass.setIdUser(user);
            usuarioClass.setNombre(nombre);
            usuarioClass.setApellido(apellido);
            usuarioClass.setMusic(music);
            usuarioClass.setInstrument(instrument);
            usuarioClass.setCountry(country);
            usuarioClass.setSex(sex);
            usuarioClass.setPassword(password);
            datos.actualizarUsuario(usuarioClass);
            Toast.makeText(EditarPerfil.this," se actualizaron los datos correctamente", Toast.LENGTH_SHORT).show();
            finish();
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

    private boolean verificarExistenciaUsuario(String user){
        Cursor c = datos.obtenerUsuarioPorId(user);
        return !(c.getCount() > 0);
    }

}
