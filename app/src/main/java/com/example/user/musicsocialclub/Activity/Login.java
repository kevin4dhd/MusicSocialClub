package com.example.user.musicsocialclub.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.musicsocialclub.Activity.Principal.ActivityPrincipal;
import com.example.user.musicsocialclub.Preferences;
import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.UsuarioClass;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout User, Password;
    private Button Ingresar;
    private OperacionesBaseDatos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        datos = OperacionesBaseDatos.obtenerInstancia(getApplicationContext());

        User = (TextInputLayout) findViewById(R.id.Login_User);
        Password = (TextInputLayout) findViewById(R.id.Login_Password);
        Ingresar = (Button) findViewById(R.id.login_ingresar);
        Ingresar.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

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
                    Preferences.savePreferenceString(Login.this,user,Preferences.PREFERENCE_USUARIO_LOGIN);
                    startActivity(new Intent(this,ActivityPrincipal.class));
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
        }

        if(id==R.id.opciones){
            Toast.makeText(this, "opciones", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
