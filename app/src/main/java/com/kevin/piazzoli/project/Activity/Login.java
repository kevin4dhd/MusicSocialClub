package com.kevin.piazzoli.project.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.kevin.piazzoli.project.Activity.Principal.ActivityPrincipal;
import com.kevin.piazzoli.project.Preferences;
import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;
import com.kevin.piazzoli.project.SQLite.UsuarioClass;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout User, Password;
    private Button Ingresar;
    private OperacionesBaseDatos datos;

    private RadioButton RBsesion;

    private boolean isActivateRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Preferences.obtenerPreferenceBoolean(this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)){
            iniciarActividadSiguiente();
        }

        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());

        User = (TextInputLayout) findViewById(R.id.Login_User);
        Password = (TextInputLayout) findViewById(R.id.Login_Password);
        Ingresar = (Button) findViewById(R.id.login_ingresar);
        Ingresar.setOnClickListener(this);

        RBsesion = (RadioButton) findViewById(R.id.Login_Sesion);

        isActivateRadioButton = RBsesion.isChecked(); //DESACTIVADO

        RBsesion.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View v) {
                if(isActivateRadioButton){
                    RBsesion.setChecked(false);
                }
                isActivateRadioButton = RBsesion.isChecked();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

    }

    private void iniciarActividadSiguiente() {
        startActivity(new Intent(this,ActivityPrincipal.class));
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        String user = User.getEditText().getText().toString();
        String Pass = Password.getEditText().getText().toString();

        boolean a = ValidarUser(user);
        boolean b = ValidarPassWord(Pass);

        if (a && b) {
            Cursor c = datos.obtenerUsuarioPorId(user);
            if(c.getCount() > 0){
                UsuarioClass usuarioClass = new UsuarioClass(c);
                if(usuarioClass.getIdUser().equals(user) && usuarioClass.getPassword().equals(Pass)){
                    Preferences.savePreferenceBoolean(Login.this,RBsesion.isChecked(),Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                    Preferences.savePreferenceString(Login.this,user,Preferences.PREFERENCE_USUARIO_LOGIN);
                    iniciarActividadSiguiente();
                    c.close();
                }else{
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
            }
            c.close();
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean ValidarUser(String Usuario){
        Pattern patron = Pattern.compile("^[a-zA-Z0-9]+$");
        if(Usuario.length() < 1) {
            User.setError("UsuarioClass Vacio");
            return false;
        }
        if (!patron.matcher(Usuario).matches() || Usuario.length() > 20) {
            User.setError("UsuarioClass Invalido");
            return false;
        } else {
            User.setError(null);
        }
        return true;
    }

    private boolean ValidarPassWord(String PassWord){
        if (PassWord.length() < 1) {
            Password.setError("Contraseña Vacia");
            return false;
        } else{
            Password.setError(null);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.crearUsuario:
                startActivity(new Intent(this,Registro.class));
                break;
            case R.id.contraseñaOlvidada:
                startActivity(new Intent(this,RecuperarPassword.class));
                break;
            case R.id.opciones:
                Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
                break;
            case R.id.codigoFuente:
                Uri uri = Uri.parse("https://github.com/kevin4dhd/MusicSocialClub");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }

        if(id==R.id.opciones){
            Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
