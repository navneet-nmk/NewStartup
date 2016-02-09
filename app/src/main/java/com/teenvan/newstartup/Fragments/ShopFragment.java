package com.teenvan.newstartup.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.teenvan.newstartup.R;

/**
 * Created by navneet on 05/02/16.
 */
public class ShopFragment extends Fragment {

    // Declaration of member variables
    private UltimateRecyclerView mShopsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_shops, container,false);

        // Referencing the UI elements
        mShopsList = (UltimateRecyclerView)rootView.findViewById(R.id.shops_list);



    return rootView;

    }
}
