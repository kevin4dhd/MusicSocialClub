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

public class fragment_recuperacion_password extends Fragment {

    private TextInputLayout User, song;
    private Button Ingresar;
    private ComunicadorChangePassword comunicadorChangePassword;
    private OperacionesBaseDatos datos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recuperacion_password,container,false);
        User = (TextInputLayout) v.findViewById(R.id.userRecuperar);
        song = (TextInputLayout) v.findViewById(R.id.songRecuperar);
        Ingresar = (Button) v.findViewById(R.id.buttonRecuperar);

        datos = OperacionesBaseDatos.obtenerInstancia(getContext());

        comunicadorChangePassword = (ComunicadorChangePassword) getActivity();

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicadorChangePassword.hideKeyboard();

                String user = User.getEditText().getText().toString();
                String music = song.getEditText().getText().toString();

                boolean a = validarTexto(User,user);
                boolean b = validarTexto(song,music);

                if (a && b) {
                    Cursor c = datos.obtenerUsuarioPorId(user);
                    if(c.getCount() > 0){
                        UsuarioClass usuarioClass = new UsuarioClass(c);
                        if(usuarioClass.getIdUser().equals(user) && usuarioClass.getMusic().equals(music)){
                            Bundle arguments = new Bundle();
                            arguments.putString("user", user);
                            fragment_change_password fragment = fragment_change_password.newInstance(arguments);
                            comunicadorChangePassword.changeFragment(fragment);
                        }else{
                            Toast.makeText(getContext(), "Respuesta secreta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
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

}
