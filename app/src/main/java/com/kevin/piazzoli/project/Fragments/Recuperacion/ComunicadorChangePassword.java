package com.kevin.piazzoli.project.Fragments.Recuperacion;

import android.support.v4.app.Fragment;

/**
 * Created by Kevin Piazzoli on 28/09/2016.
 */

public interface ComunicadorChangePassword {
    void changeFragment(Fragment fragment);
    void finalizar();
    void hideKeyboard();
}
