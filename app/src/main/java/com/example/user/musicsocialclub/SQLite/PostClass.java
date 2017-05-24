package com.example.user.musicsocialclub.SQLite;

import android.database.Cursor;

/**
 * Created by user on 21/05/2017.
 */

public class PostClass {

    private String idGenerate;
    private String idUser;
    private String nombre;
    private String apellido;
    private String post;
    private String type;
    private String hora;

    private String nombreMusic;
    private String horaMusic;
    private String dataMusic;

    public PostClass(Cursor cursor,boolean cNext){
        if(cNext)cursor.moveToFirst();
        this.idGenerate = cursor.getString(0);
        this.idUser = cursor.getString(2);
        this.nombre = cursor.getString(3);
        this.apellido = cursor.getString(4);
        this.post = cursor.getString(5);
        this.type = cursor.getString(7);
        this.hora = cursor.getString(6);
        this.nombreMusic = cursor.getString(8);
        this.horaMusic = cursor.getString(9);
        this.dataMusic = cursor.getString(10);
    }

    public PostClass(String idGenerate, String idUser, String nombre, String apellido, String post, String type, String hora, String nombreMusic, String horaMusic, String dataMusic) {
        this.idGenerate = idGenerate;
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.post = post;
        this.type = type;
        this.hora = hora;
        this.nombreMusic = nombreMusic;
        this.horaMusic = horaMusic;
        this.dataMusic = dataMusic;
    }

    public String getIdGenerate() {
        return idGenerate;
    }

    public void setIdGenerate(String idGenerate) {
        this.idGenerate = idGenerate;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public String getDataMusic() {
        return dataMusic;
    }

    public void setDataMusic(String dataMusic) {
        this.dataMusic = dataMusic;
    }

}
