package com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.Dialog;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kevin.piazzoli.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 21/05/2017.
 */

public class FragmentInternalAudio extends Fragment {

    private RecyclerView rv;
    private List<AudioAtributos> atributosList;
    private AudioAdapterRecyclerView adapterAudio;
    private LinearLayout layoutSinMusic;

    public class TareaPruebaDatos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DURATION
            };

            Cursor cursor = getActivity().managedQuery(
                    MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);

            while(cursor.moveToNext()){
                agregarMusic(cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(5)));
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterAudio.notifyDataSetChanged();
            verificarSiTenemosMusica();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_audio,container,false);

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.recyclerViewAudio);
        layoutSinMusic = (LinearLayout) v.findViewById(R.id.layoutVacioSinMusica);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        DialogFragmentSelectAudio dialogFragmentSelectAudio = (DialogFragmentSelectAudio)getParentFragment();

        adapterAudio = new AudioAdapterRecyclerView(atributosList,getContext(),dialogFragmentSelectAudio);
        rv.setAdapter(adapterAudio);
        new TareaPruebaDatos().execute();
        adapterAudio.notifyDataSetChanged();
        verificarSiTenemosMusica();

        return v;
    }

    public void verificarSiTenemosMusica(){
        if(atributosList.isEmpty()){
            layoutSinMusic.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            layoutSinMusic.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void agregarMusic(String nombre, String data,int Duracion){
        AudioAtributos postAtributos = new AudioAtributos();
        postAtributos.setNameSong(nombre);
        postAtributos.setData(data);
        postAtributos.setPlay(false);
        postAtributos.setMediaPlayer(MediaPlayer.create(getContext(), Uri.parse(data)));
        int hora = Duracion/3600000;
        int restohora = Duracion%3600000;
        int minuto = restohora/60000;
        int restominuto = restohora%60000;
        int segundo = restominuto/1000 ;
        postAtributos.setDuracion((hora<10 ? "0"+hora : hora) + ":" + (minuto<10 ? "0"+minuto : minuto) + ":" + (segundo<10 ? "0"+segundo : segundo));
        atributosList.add(postAtributos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(int i=0;i<atributosList.size();i++){
            if(atributosList.get(i).getMediaPlayer()!=null){
                atributosList.get(i).getMediaPlayer().stop();
                atributosList.get(i).setMediaPlayer(null);
            }
        }
    }


}
