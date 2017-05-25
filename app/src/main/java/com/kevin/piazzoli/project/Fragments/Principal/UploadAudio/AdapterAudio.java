package com.kevin.piazzoli.project.Fragments.Principal.UploadAudio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.Dialog.FragmentExternalAudio;
import com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.Dialog.FragmentInternalAudio;

/**
 * Created by user on 21/05/2017.
 */

public class AdapterAudio extends FragmentPagerAdapter {


    public AdapterAudio(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return new FragmentInternalAudio();
        else if(position==1)return new FragmentExternalAudio();
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Interno";
        else if(position==1)return "SD Card";
        return null;
    }
}

