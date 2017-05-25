package com.kevin.piazzoli.project.Fragments.Recuperacion;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;
import com.kevin.piazzoli.project.SQLite.UsuarioClass;

/**
 * Created by user on 21/05/2017.
 */

public class fragment_change_password extends Fragment {

    private TextInputLayout password, passwordRepeat;
    private Button Ingresar;
    private ComunicadorChangePassword comunicadorChangePassword;
    private OperacionesBaseDatos datos;

    private String user;

    public static fragment_change_password newInstance(Bundle arguments){
        fragment_change_password f = new fragment_change_password();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password,container,false);
        password = (TextInputLayout) v.findViewById(R.id.password);
        passwordRepeat = (TextInputLayout) v.findViewById(R.id.repetirPassword);
        Ingresar = (Button) v.findViewById(R.id.buttonRecuperar);

        if(getArguments()!=null){
            user = getArguments().getString("user");
        }

        datos = OperacionesBaseDatos.obtenerInstancia(getContext());

        comunicadorChangePassword = (ComunicadorChangePassword) getActivity();

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorChangePassword.hideKeyboard();

                String passwordText = password.getEditText().getText().toString();
                String passwordRepeatText = passwordRepeat.getEditText().getText().toString();

                boolean va = validarTexto(password,passwordText) && validarTexto(passwordRepeat,passwordRepeatText);

                if(va){
                    if(validarPassword(passwordRepeat,passwordText,passwordRepeatText)){
                        Cursor c = datos.obtenerUsuarioPorId(user);
                        UsuarioClass usuarioClass = new UsuarioClass(c);
                        usuarioClass.setPassword(passwordText);
                        datos.actualizarUsuario(usuarioClass);
                        Toast.makeText(getContext(), "La contraseña se cambio correctamente", Toast.LENGTH_SHORT).show();
                        comunicadorChangePassword.finalizar();
                    }
                }
            }
        });
        return v;
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

}
