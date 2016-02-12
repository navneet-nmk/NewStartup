package com.teenvan.newstartup.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teenvan.newstartup.R;

/**
 * Created by navneet on 05/02/16.
 */
public class SecondFragment extends Fragment {

    // Declaration of member variables
    private TextView mAppTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_page_one, container,false);

        // Referencing the UI elements
        mAppTitle = (TextView)rootView.findViewById(R.id.appTitle);

    return rootView;

    }
}
