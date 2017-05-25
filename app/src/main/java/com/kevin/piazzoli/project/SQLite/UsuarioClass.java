package com.kevin.piazzoli.project.SQLite;

import android.database.Cursor;

/**
 * Created by user on 20/05/2017.
 */

public class UsuarioClass {

    private String idGenerate;
    private String idUser;
    private String nombre;
    private String apellido;
    private String music;
    private String password;
    private String instrument;
    private String country;
    private String Sex;

    public UsuarioClass(){

    }

    public UsuarioClass(String idGenerate, String idUser, String nombre, String apellido, String music, String password, String instrument, String country, String sex) {
        this.idGenerate = idGenerate;
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.music = music;
        this.password = password;
        this.instrument = instrument;
        this.country = country;
        Sex = sex;
    }

    public UsuarioClass(Cursor cursor){
        cursor.moveToFirst();
        this.idGenerate = cursor.getString(0);
        this.idUser = cursor.getString(1);
        this.nombre = cursor.getString(2);
        this.apellido = cursor.getString(3);
        this.music = cursor.getString(4);
        this.password = cursor.getString(5);
        this.instrument = cursor.getString(6);
        this.country = cursor.getString(7);
        Sex = cursor.getString(8);
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

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}
