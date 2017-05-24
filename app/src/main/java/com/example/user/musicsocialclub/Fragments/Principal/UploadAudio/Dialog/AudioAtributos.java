package com.example.user.musicsocialclub.Fragments.Principal.UploadAudio.Dialog;

import android.media.MediaPlayer;

/**
 * Created by user on 21/05/2017.
 */

public class AudioAtributos {

    private String nameSong;
    private String duracion;
    private String data;
    private boolean play;
    MediaPlayer mediaPlayer;

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
