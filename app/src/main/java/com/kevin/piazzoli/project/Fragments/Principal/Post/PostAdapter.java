package com.kevin.piazzoli.project.Fragments.Principal.Post;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.piazzoli.project.Preferences;
import com.kevin.piazzoli.project.R;
import com.kevin.piazzoli.project.SQLite.OperacionesBaseDatos;

import java.util.List;

/**
 * Created by user on 8/05/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HolderAmigos> {

    private List<PostAtributos> atributosList;
    private Context context;
    private OperacionesBaseDatos datos;

    public PostAdapter(List<PostAtributos> atributosList, Context context,OperacionesBaseDatos datos){
        this.atributosList = atributosList;
        this.context = context;
        this.datos = datos;
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

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(atributosList.get(position).getId().equalsIgnoreCase(Preferences.obtenerPreferenceString(context,Preferences.PREFERENCE_USUARIO_LOGIN))){
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("¿Estas seguro de eliminar esta publicación?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            datos.eliminarPost(atributosList.get(position).getIdGenerate());
                                            atributosList.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    })
                            .setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                }
                return true;
            }
        });

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
            imageView = (ImageView) itemView.findViewById(R.id.fotoPerfilPost);
            nombre = (TextView) itemView.findViewById(R.id.nombreUsuarioAmigo);
            post = (TextView) itemView.findViewById(R.id.mensajeAmigos);
            switch (type){
                case 1:
                    cardView = (CardView) itemView.findViewById(R.id.cardViewPostMusic);

                    hora = (TextView) itemView.findViewById(R.id.horaAmigos);
                    break;
                case 2:
                    cardView = (CardView) itemView.findViewById(R.id.cardViewPostMusic);
                    hora = (TextView) itemView.findViewById(R.id.horaAmigos);
                    nombreMusic = (TextView) itemView.findViewById(R.id.nameMusic);
                    horaMusic = (TextView) itemView.findViewById(R.id.durationMusic);
                    play = (ImageButton) itemView.findViewById(R.id.playMusic);
                    break;
            }
        }
    }



}
