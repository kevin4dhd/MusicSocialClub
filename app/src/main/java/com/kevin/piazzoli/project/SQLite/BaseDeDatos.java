package com.kevin.piazzoli.project.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import com.kevin.piazzoli.project.SQLite.BDusuarios.*;

/**
 * Created by user on 20/05/2017.
 */

public class BaseDeDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "pedidos.db";
    private static final int VERSION_ACTUAL = 1;
    private final Context contexto;

    interface Tablas {
        String USUARIOS = "usuarios";
        String POST = "table_post";
    }

    public BaseDeDatos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s ( " +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s )",
                Tablas.USUARIOS, BaseColumns._ID,
                users.COLUMNA_ID,
                users.COLUMNA_USER,
                users.COLUMNA_NOMBRE,
                users.COLUMNA_APELLIDOS,
                users.COLUMNA_MUSIC,
                users.COLUMNA_PASSWORD,
                users.COLUMNA_INSTRUMENT,
                users.COLUMNA_COUNTRY,
                users.COLUMNA_SEX));

        db.execSQL(String.format("CREATE TABLE %s ( " +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s " +
                        "TEXT NOT NULL,%s )",
                Tablas.POST, BaseColumns._ID,
                post.COLUMNA_ID,
                post.COLUMNA_USER,
                post.COLUMNA_NOMBRE,
                post.COLUMNA_APELLIDOS,
                post.COLUMNA_POST,
                post.COLUMNA_HORA,
                post.COLUMNA_TYPE_POST,
                post.COLUMNA_NAME_MUSIC,
                post.COLUMNA_DURACION_MUSIC,
                post.COLUMNA_DATA_MUSIC
                ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.POST);
        onCreate(db);
    }

}
