package com.example.user.musicsocialclub.Fragments.Principal.Post;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.musicsocialclub.Activity.Principal.ActivityPrincipal;
import com.example.user.musicsocialclub.R;
import com.example.user.musicsocialclub.SQLite.OperacionesBaseDatos;
import com.example.user.musicsocialclub.SQLite.PostClass;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 8/05/2017.
 */

public class FragmentPost extends Fragment {

    private static RecyclerView rv;
    private static List<PostAtributos> atributosList;
    private OperacionesBaseDatos datos;
    private static LinearLayout layoutSinSolicitudes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post,container,false);

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.amigosRecyclerView);
        layoutSinSolicitudes = (LinearLayout) v.findViewById(R.id.layoutVacioSolicitudes);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        ActivityPrincipal.postAdapter = new PostAdapter(atributosList,getContext());
        rv.setAdapter(ActivityPrincipal.postAdapter);

        datos = OperacionesBaseDatos.obtenerInstancia(getContext());

        Cursor c = datos.obtenerPost();

        if (c.moveToFirst()) {
            do {
                PostClass postClass = new PostClass(c,false);
                agregarPost(postClass,getContext());
            } while(c.moveToNext());
        }
        c.close();

        verificarSiTenemosPost();

        return v;
    }

    public static void verificarSiTenemosPost(){
        if(atributosList.isEmpty()){
            layoutSinSolicitudes.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            layoutSinSolicitudes.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public static void agregarPost(PostClass postClass,Context c){
        PostAtributos postAtributos = new PostAtributos();
        postAtributos.setFotoDePerfil(R.drawable.ic_action_user);
        postAtributos.setNombre(postClass.getNombre()+" "+postClass.getApellido());
        postAtributos.setPost(postClass.getPost());
        postAtributos.setId(postClass.getIdUser());
        postAtributos.setHora(postClass.getHora());
        postAtributos.setType(postClass.getType());
        postAtributos.setPlay(false);
        postAtributos.setMediaPlayer(MediaPlayer.create(c, Uri.parse(postClass.getDataMusic())));
        postAtributos.setNombreMusic(postClass.getNombreMusic());
        postAtributos.setHoraMusic(postClass.getHoraMusic());
        if(atributosList.isEmpty()){
            atributosList.add(postAtributos);
        }else{
            List<PostAtributos> atributoses = new ArrayList<>();
            atributoses.add(postAtributos);
            for(int i=00;i<atributosList.size();i++){
                atributoses.add(atributosList.get(i));
            }
            atributosList.clear();
            for(int i=0;i<atributoses.size();i++){
                atributosList.add(atributoses.get(i));
            }
        }
        verificarSiTenemosPost();
        ActivityPrincipal.postAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        for(int i=0;i<atributosList.size();i++){
            if(atributosList.get(i).getMediaPlayer()!=null){
                atributosList.get(i).getMediaPlayer().stop();
                atributosList.get(i).setMediaPlayer(null);
            }
        }

    }
}
