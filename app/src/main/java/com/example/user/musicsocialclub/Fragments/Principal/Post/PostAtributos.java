package com.example.user.musicsocialclub.Fragments.Principal.Post;

import android.media.MediaPlayer;

/**
 * Created by user on 8/05/2017.
 */

public class PostAtributos {

    private int fotoDePerfil;
    private String nombre;
    private String post;
    private String id;
    private String hora;
    private String type;

    private String nombreMusic;
    private String horaMusic;
    private MediaPlayer mediaPlayer;
    private boolean play;

    public PostAtributos(){

    }

    public int getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(int fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNombreMusic() {
        return nombreMusic;
    }

    public void setNombreMusic(String nombreMusic) {
        this.nombreMusic = nombreMusic;
    }

    public String getHoraMusic() {
        return horaMusic;
    }

    public void setHoraMusic(String horaMusic) {
        this.horaMusic = horaMusic;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
