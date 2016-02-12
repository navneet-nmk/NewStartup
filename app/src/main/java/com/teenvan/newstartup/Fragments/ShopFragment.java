package com.teenvan.newstartup.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teenvan.newstartup.Adapters.ShopListAdapter;
import com.teenvan.newstartup.Api.BridgeApi;
import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;
import com.teenvan.newstartup.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by navneet on 05/02/16.
 */
public class ShopFragment extends Fragment {

    // Declaration of member variables
    private RecyclerView mShopsList;
    private ArrayList<Shop> mShops;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_shops, container,false);

        Bundle arguments = getArguments();
        mShops = arguments.getParcelableArrayList("Shops");

        //add cache to the client



        // Referencing the UI elements
        mShopsList = (RecyclerView)rootView.findViewById(R.id.shopsList);
        mShopsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopsList.setHasFixedSize(true);

        // Setting the adapter
        if(!mShops.isEmpty()){
            ShopListAdapter adapter = new ShopListAdapter(mShops, getActivity());
            mShopsList.setAdapter(adapter);
        }


    return rootView;

    }







}
