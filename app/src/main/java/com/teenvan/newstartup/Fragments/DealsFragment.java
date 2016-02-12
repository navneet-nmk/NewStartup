package com.teenvan.newstartup.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teenvan.newstartup.Adapters.DealListAdapter;
import com.teenvan.newstartup.Model.Deal;
import com.teenvan.newstartup.R;

import java.util.ArrayList;

/**
 * Created by navneet on 05/02/16.
 */
public class DealsFragment extends Fragment {

    // Declaration of member variables
    private RecyclerView mDealsList;
    private ArrayList<Deal> mDeals;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_deals, container,false);

        mDeals = getArguments().getParcelableArrayList("Deals");


        // Referencing the UI elements
        mDealsList = (RecyclerView)rootView.findViewById(R.id.dealsList);
        mDealsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDealsList.setHasFixedSize(true);

        if(!mDeals.isEmpty()){
            DealListAdapter adapter = new DealListAdapter(mDeals, getActivity());
            mDealsList.setAdapter(adapter);
        }

    return rootView;

    }
}
