package com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.Dialog;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kevin.piazzoli.project.Fragments.Principal.UploadAudio.AdapterAudio;
import com.kevin.piazzoli.project.R;

/**
 * Created by user on 21/05/2017.
 */

public class DialogFragmentSelectAudio extends DialogFragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.activity_select_audio, container);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayoutAudios);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerAudios);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new AdapterAudio(getChildFragmentManager()));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

}


