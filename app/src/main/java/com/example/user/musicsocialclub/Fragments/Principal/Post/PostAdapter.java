package com.example.user.musicsocialclub.Fragments.Principal.Post;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.musicsocialclub.R;

import java.util.List;

/**
 * Created by user on 8/05/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HolderAmigos> {

    private List<PostAtributos> atributosList;
    private Context context;

    public PostAdapter(List<PostAtributos> atributosList, Context context){
        this.atributosList = atributosList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(atributosList.get(position).getType());
    }

    @Override
    public HolderAmigos onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: return new HolderAmigos(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_posts,parent,false),1);
            case 2: return new HolderAmigos(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_posts_type_2,parent,false),2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final HolderAmigos holder, final int position) {
        holder.imageView.setImageResource(atributosList.get(position).getFotoDePerfil());
        holder.nombre.setText(atributosList.get(position).getNombre());
        holder.post.setText(atributosList.get(position).getPost());
        holder.hora.setText(atributosList.get(position).getHora());

        if(atributosList.get(position).getType().equals("2")){
            holder.nombreMusic.setText(atributosList.get(position).getNombreMusic());
            holder.horaMusic.setText(atributosList.get(position).getHoraMusic());
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
        }

    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class HolderAmigos extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imageView;
        TextView nombre;
        TextView post;
        TextView hora;

        TextView nombreMusic;
        TextView horaMusic;
        ImageButton play;

        public HolderAmigos(View itemView,int type) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewMusic);
            imageView = (ImageView) itemView.findViewById(R.id.fotoDePerfilAmigos);
            nombre = (TextView) itemView.findViewById(R.id.nombreUsuarioAmigo);
            post = (TextView) itemView.findViewById(R.id.mensajeAmigos);
            hora = (TextView) itemView.findViewById(R.id.horaAmigos);
            if(type==2){
                nombreMusic = (TextView) itemView.findViewById(R.id.nameMusic);
                horaMusic = (TextView) itemView.findViewById(R.id.durationMusic);
                play = (ImageButton) itemView.findViewById(R.id.playMusic);
            }
        }
    }



}
