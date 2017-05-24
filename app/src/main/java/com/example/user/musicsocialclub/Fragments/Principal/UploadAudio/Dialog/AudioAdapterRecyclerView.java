package com.example.user.musicsocialclub.Fragments.Principal.UploadAudio.Dialog;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.user.musicsocialclub.Fragments.Principal.ComunicadorPrincipal;
import com.example.user.musicsocialclub.Preferences;
import com.example.user.musicsocialclub.R;

import java.util.List;

/**
 * Created by user on 8/05/2017.
 */

public class AudioAdapterRecyclerView extends RecyclerView.Adapter<AudioAdapterRecyclerView.AudioHolder> {

    private List<AudioAtributos> atributosList;
    private Context context;
    private DialogFragmentSelectAudio dialogFragmentSelectAudio;

    public AudioAdapterRecyclerView(List<AudioAtributos> atributosList, Context context,DialogFragmentSelectAudio dialogFragmentSelectAudio){
        this.atributosList = atributosList;
        this.context = context;
        this.dialogFragmentSelectAudio = dialogFragmentSelectAudio;
    }

    @Override
    public AudioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_audio,parent,false);
        return new AudioHolder(v);
    }

    @Override
    public void onBindViewHolder(final AudioHolder holder, final int position) {
        holder.nombre.setText(atributosList.get(position).getNameSong());
        holder.hora.setText(atributosList.get(position).getDuracion());
        holder.nombre.setSelected(true);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = atributosList.get(position).getMediaPlayer();
                if(atributosList.get(position).isPlay()){
                    atributosList.get(position).setPlay(false);
                    holder.play.setImageResource(R.drawable.ic_action_playback_play);
                    mp.pause();
                }else{
                    mp.seekTo(0);
                    mp.start();
                    atributosList.get(position).setPlay(true);
                    holder.play.setImageResource(R.drawable.ic_action_playback_pause);
                }
            }
        });

        holder.subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<atributosList.size();i++){
                    if(atributosList.get(i).getMediaPlayer()!=null){
                        atributosList.get(i).getMediaPlayer().stop();
                        atributosList.get(i).setMediaPlayer(null);
                    }
                }
                Preferences.savePreferenceString(context,atributosList.get(position).getData(),Preferences.PREFERENCE_AUDIO_TEMP);
                Preferences.savePreferenceString(context,atributosList.get(position).getNameSong(),Preferences.PREFERENCE_NAME_AUDIO);
                Preferences.savePreferenceString(context,atributosList.get(position).getDuracion(),Preferences.PREFERENCE_DURACION_TEMP);
                ComunicadorPrincipal comunicadorAudio = (ComunicadorPrincipal) dialogFragmentSelectAudio.getActivity();
                comunicadorAudio.actualizarFragmentAudio();
                dialogFragmentSelectAudio.dismiss();

            }
        });
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class AudioHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView nombre;
        TextView hora;
        ImageButton play;
        ImageButton subir;

        public AudioHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewAmigos);
            nombre = (TextView) itemView.findViewById(R.id.nameMusic);
            hora = (TextView) itemView.findViewById(R.id.durationMusic);
            subir = (ImageButton) itemView.findViewById(R.id.elegirMusic);
            play = (ImageButton) itemView.findViewById(R.id.playMusic);
        }
    }

}
