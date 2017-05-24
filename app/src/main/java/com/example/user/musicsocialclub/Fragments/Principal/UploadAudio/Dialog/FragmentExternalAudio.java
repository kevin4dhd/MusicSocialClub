package com.example.user.musicsocialclub.Fragments.Principal.UploadAudio.Dialog;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.musicsocialclub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 21/05/2017.
 */

public class FragmentExternalAudio extends Fragment {

    private RecyclerView rv;
    private List<AudioAtributos> atributosList;
    private AudioAdapterRecyclerView adapterAudio;
    private LinearLayout layoutSinMusic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_audio,container,false);

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.recyclerViewAudio);
        layoutSinMusic = (LinearLayout) v.findViewById(R.id.layoutVacioSinMusica);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapterAudio = new AudioAdapterRecyclerView(atributosList,getContext(),(DialogFragmentSelectAudio)getParentFragment());
        rv.setAdapter(adapterAudio);

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
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        //List<String> songs = new ArrayList<String>();
        while(cursor.moveToNext()){
            //songs.add(cursor.getString(0) + "||" + cursor.getString(1) + "||" +   cursor.getString(2) + "||" +   cursor.getString(3) + "||" +  cursor.getString(4) + "||" +  cursor.getString(5));
            agregarMusic(cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(5)));
        }
        cursor.close();


        /*ContentResolver cr = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);

        int count = 0;

        if(cur != null) {
            count = cur.getCount();
            if(count > 0) {
                while(cur.moveToNext()) {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    MediaPlayer mediaPlayer=MediaPlayer.create(getContext(),Uri.parse(data));
                    //mediaPlayer.start();
                    agregarMusic(String track = intent.getStringExtra("track");,"00:00");
                }
            }
            cur.close();
        }*/

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
        postAtributos.setMediaPlayer(MediaPlayer.create(getContext(), Uri.parse(data)));
        int hora = Duracion/3600000;
        int restohora = Duracion%3600000;
        int minuto = restohora/60000;
        int restominuto = restohora%60000;
        int segundo = restominuto/1000 ;
        postAtributos.setDuracion((hora<10 ? "0"+hora : hora) + ":" + (minuto<10 ? "0"+minuto : minuto) + ":" + (segundo<10 ? "0"+segundo : segundo));
        atributosList.add(postAtributos);
        verificarSiTenemosMusica();
        adapterAudio.notifyDataSetChanged();
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
