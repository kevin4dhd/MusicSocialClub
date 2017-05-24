package com.example.user.musicsocialclub.Fragments.Principal.UploadAudio;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.musicsocialclub.Fragments.Principal.ComunicadorPrincipal;
import com.example.user.musicsocialclub.Fragments.Principal.Post.FragmentPost;
import com.example.user.musicsocialclub.Fragments.Principal.UploadAudio.Dialog.DialogFragmentSelectAudio;
import com.example.user.musicsocialclub.Preferences;
import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.PostClass;
import com.example.user.musicsocialclub.SQLite.UsuarioClass;

import java.util.Date;

/**
 * Created by user on 21/05/2017.
 */

public class FragmentUploadAudio extends Fragment implements ComunicadorAudio {

    private Button elegirAudio;
    private TextView audioFileText;
    private TextInputLayout textPost;
    private Button publicar;
    private OperacionesBaseDatos datos;
    private UsuarioClass usuarioClass;
    private ComunicadorPrincipal comunicadorPrincipal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_audio,container,false);

        elegirAudio = (Button) v.findViewById(R.id.ElegirArchivo);
        audioFileText = (TextView) v.findViewById(R.id.audioFileText);
        textPost = (TextInputLayout) v.findViewById(R.id.post);
        publicar = (Button) v.findViewById(R.id.publicar);

        datos = OperacionesBaseDatos.obtenerInstancia(getContext());
        usuarioClass = new UsuarioClass(datos.obtenerUsuarioPorId(Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN)));
        comunicadorPrincipal = (ComunicadorPrincipal) getActivity();


        elegirAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DialogFragmentSelectAudio dialog = new DialogFragmentSelectAudio();
                dialog.show(fragmentManager, "Elige un Audio");
            }
        });

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence s  = DateFormat.format("HH:mm",  new Date().getTime());
                PostClass p = new PostClass(
                        null,
                        usuarioClass.getIdUser(),
                        usuarioClass.getNombre(),
                        usuarioClass.getApellido(),
                        textPost.getEditText().getText().toString(),
                        "2",""+s,Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_NAME_AUDIO),
                        Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_DURACION_TEMP),
                        Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_AUDIO_TEMP));
                datos.insertarPost(p);
                FragmentPost.agregarPost(p,getContext());
                textPost.getEditText().setText("");
                comunicadorPrincipal.actualizarPost();
                comunicadorPrincipal.hideKeyboard();
            }
        });

        actualizar();

        return v;
    }

    @Override
    public void actualizar() {
        audioFileText.setText(Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_NAME_AUDIO));
    }
}
