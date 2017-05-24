package com.example.user.musicsocialclub.Fragments.Recuperacion;

import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.widget.ImageButton;

/**
 * Created by Kevin Piazzoli on 28/09/2016.
 */

public interface ComunicadorChangePassword {
    void changeFragment(Fragment fragment);
    void finalizar();
    void hideKeyboard();
}
