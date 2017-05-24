package com.example.user.musicsocialclub.SQLite;

import java.util.UUID;

/**
 * Created by user on 20/05/2017.
 */

public class BDusuarios {

    interface ColumnasUsuario {
        String COLUMNA_ID = "id";
        String COLUMNA_USER = "user";
        String COLUMNA_NOMBRE = "nombre";
        String COLUMNA_APELLIDOS = "apellidos";
        String COLUMNA_MUSIC = "music";
        String COLUMNA_PASSWORD = "password";
        String COLUMNA_INSTRUMENT = "instrument";
        String COLUMNA_COUNTRY = "country";
        String COLUMNA_SEX = "sex";
    }

    interface ColumnasPost {
        String COLUMNA_ID = "id";
        String COLUMNA_USER = "user";
        String COLUMNA_NOMBRE = "nombre";
        String COLUMNA_APELLIDOS = "apellidos";
        String COLUMNA_POST = "post";
        String COLUMNA_HORA = "hora";
        String COLUMNA_TYPE_POST = "type";
        String COLUMNA_NAME_MUSIC = "namemusic";
        String COLUMNA_DURACION_MUSIC = "duracionmusic";
        String COLUMNA_DATA_MUSIC = "datamusic";
    }

    public static class users implements ColumnasUsuario{
        public static String generarIdUsuario() {
            return "USER-" + UUID.randomUUID().toString();
        }
    }

    public static class post implements ColumnasPost{
        public static String generateIdPost() {
            return "POST-" + UUID.randomUUID().toString();
        }
    }

}
