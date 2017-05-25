package com.kevin.piazzoli.project.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.kevin.piazzoli.project.SQLite.BDusuarios.*;

/**
 * Created by user on 20/05/2017.
 */

public class OperacionesBaseDatos {

    private static BaseDeDatos baseDatos;

    private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    public SQLiteDatabase getDb() {
        return baseDatos.getWritableDatabase();
    }

    private OperacionesBaseDatos() {
    }

    public static OperacionesBaseDatos obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new BaseDeDatos(contexto);
        }
        return instancia;
    }

    // [Operaciones Usuarios]
    public Cursor obtenerUsuarios() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDeDatos.Tablas.USUARIOS);
        return db.rawQuery(sql, null);
    }

    public Cursor obtenerUsuarioPorId(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=?", users.COLUMNA_USER);
        String[] selectionArgs = {id};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(BaseDeDatos.Tablas.USUARIOS);

        String[] proyeccion = {
                BaseDeDatos.Tablas.USUARIOS + "." + users.COLUMNA_ID,
                users.COLUMNA_USER,
                users.COLUMNA_NOMBRE,
                users.COLUMNA_APELLIDOS,
                users.COLUMNA_MUSIC,
                users.COLUMNA_PASSWORD,
                users.COLUMNA_INSTRUMENT,
                users.COLUMNA_COUNTRY,
                users.COLUMNA_SEX
                };

        return builder.query(db, proyeccion, selection, selectionArgs, null, null, null);
    }

    public String insertarUsario(UsuarioClass cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idCliente = BDusuarios.users.generarIdUsuario();

        ContentValues valores = new ContentValues();
        valores.put(users.COLUMNA_ID, idCliente);
        valores.put(users.COLUMNA_USER, cliente.getIdUser());
        valores.put(users.COLUMNA_NOMBRE, cliente.getNombre());
        valores.put(users.COLUMNA_APELLIDOS, cliente.getApellido());
        valores.put(users.COLUMNA_MUSIC, cliente.getMusic());
        valores.put(users.COLUMNA_PASSWORD, cliente.getPassword());
        valores.put(users.COLUMNA_INSTRUMENT, cliente.getInstrument());
        valores.put(users.COLUMNA_COUNTRY, cliente.getCountry());
        valores.put(users.COLUMNA_SEX, cliente.getSex());

        return db.insertOrThrow(BaseDeDatos.Tablas.USUARIOS, null, valores) > 0 ? idCliente : null;
    }

    public boolean actualizarUsuario(UsuarioClass usuario) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(users.COLUMNA_USER, usuario.getIdUser());
        valores.put(users.COLUMNA_NOMBRE, usuario.getNombre());
        valores.put(users.COLUMNA_APELLIDOS, usuario.getApellido());
        valores.put(users.COLUMNA_MUSIC, usuario.getMusic());
        valores.put(users.COLUMNA_PASSWORD, usuario.getPassword());
        valores.put(users.COLUMNA_INSTRUMENT, usuario.getInstrument());
        valores.put(users.COLUMNA_COUNTRY, usuario.getCountry());
        valores.put(users.COLUMNA_SEX, usuario.getSex());

        String whereClause = String.format("%s=?", users.COLUMNA_ID);
        final String[] whereArgs = {usuario.getIdGenerate()};

        int resultado = db.update(BaseDeDatos.Tablas.USUARIOS, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarUsuario(String idCliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", BDusuarios.users.COLUMNA_ID);
        final String[] whereArgs = {idCliente};

        int resultado = db.delete(BaseDeDatos.Tablas.USUARIOS, whereClause, whereArgs);

        return resultado > 0;
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    // [Operaciones POST]
    public Cursor obtenerPost() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDeDatos.Tablas.POST);
        return db.rawQuery(sql, null);
    }

    public Cursor obtenerPostPorId(String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String selection = String.format("%s=?", post.COLUMNA_USER);
        String[] selectionArgs = {id};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(BaseDeDatos.Tablas.POST);

        String[] proyeccion = {
                BaseDeDatos.Tablas.POST + "." + post.COLUMNA_ID,
                post.COLUMNA_USER,
                post.COLUMNA_NOMBRE,
                post.COLUMNA_APELLIDOS,
                post.COLUMNA_POST,
                post.COLUMNA_HORA,
                post.COLUMNA_TYPE_POST,
                post.COLUMNA_NAME_MUSIC,
                post.COLUMNA_DURACION_MUSIC,
                post.COLUMNA_DATA_MUSIC
        };

        return builder.query(db, proyeccion, selection, selectionArgs, null, null, null);
    }

    public String insertarPost(PostClass postClass) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idPost = BDusuarios.post.generateIdPost();

        ContentValues valores = new ContentValues();
        valores.put(post.COLUMNA_ID, idPost);
        valores.put(post.COLUMNA_USER, postClass.getIdUser());
        valores.put(post.COLUMNA_NOMBRE, postClass.getNombre());
        valores.put(post.COLUMNA_APELLIDOS, postClass.getApellido());
        valores.put(post.COLUMNA_POST, postClass.getPost());
        valores.put(post.COLUMNA_HORA, postClass.getHora());
        valores.put(post.COLUMNA_TYPE_POST, postClass.getType());
        valores.put(post.COLUMNA_NAME_MUSIC, postClass.getNombreMusic());
        valores.put(post.COLUMNA_DURACION_MUSIC, postClass.getHoraMusic());
        valores.put(post.COLUMNA_DATA_MUSIC, postClass.getDataMusic());

        return db.insertOrThrow(BaseDeDatos.Tablas.POST, null, valores) > 0 ? idPost : null;
    }

    public boolean eliminarPost(String post) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", BDusuarios.post.COLUMNA_ID);
        final String[] whereArgs = {post};

        int resultado = db.delete(BaseDeDatos.Tablas.POST, whereClause, whereArgs);

        return resultado > 0;
    }

}

