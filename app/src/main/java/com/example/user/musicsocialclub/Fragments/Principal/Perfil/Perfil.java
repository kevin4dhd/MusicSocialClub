package com.example.user.musicsocialclub.Fragments.Principal.Perfil;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.musicsocialclub.Fragments.Principal.Post.FragmentPost;
import com.example.user.musicsocialclub.Fragments.Principal.ComunicadorPrincipal;
import com.example.user.musicsocialclub.Fragments.Principal.Post.PostAtributos;
import com.example.user.musicsocialclub.Preferences;
import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.PostClass;
import com.example.user.musicsocialclub.SQLite.UsuarioClass;

import java.util.Date;

/**
 * Created by user on 21/05/2017.
 */

public class Perfil extends Fragment {

    private OperacionesBaseDatos datos;

    private TextView nombre,sex,instrumento,pais;
    private TextInputLayout textPost;
    private Button publicar;
    private ComunicadorPrincipal comunicadorPrincipal;
    private UsuarioClass usuarioClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        datos = OperacionesBaseDatos.obtenerInstancia(getContext());

        comunicadorPrincipal = (ComunicadorPrincipal) getActivity();

        usuarioClass = new UsuarioClass(datos.obtenerUsuarioPorId(Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN)));

        nombre = (TextView) v.findViewById(R.id.nombre);
        instrumento = (TextView) v.findViewById(R.id.instrumento);
        sex = (TextView) v.findViewById(R.id.sex);
        pais = (TextView) v.findViewById(R.id.pais);

        nombre.setText(usuarioClass.getNombre());
        instrumento.setText(usuarioClass.getInstrument().isEmpty() ? "No tienes ningun instrumento" : usuarioClass.getInstrument());
        if(usuarioClass.getSex().equals("hombre")){
            sex.setText("Hombre");
        }else{
            sex.setText("Mujer");
        }
        pais.setText(usuarioClass.getCountry());
        publicar = (Button) v.findViewById(R.id.publicar);
        textPost = (TextInputLayout) v.findViewById(R.id.post);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence s  = DateFormat.format("HH:mm",  new Date().getTime());
                PostClass p = new PostClass(null,usuarioClass.getIdUser(),usuarioClass.getNombre(),usuarioClass.getApellido(),textPost.getEditText().getText().toString(),"1",""+s,"","","");
                datos.insertarPost(p);
                FragmentPost.agregarPost(p,getContext());
                textPost.getEditText().setText("");
                comunicadorPrincipal.actualizarPost();
                comunicadorPrincipal.hideKeyboard();
            }
        });

        return v;
    }
}
