package com.teenvan.newstartup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by navneet on 30/10/15.
 */
public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Declaration of member variables
    private Context mContext;
    private ArrayList<Shop> mShops;

    ShopListAdapter(ArrayList<Shop> mShops,
                    Context context){
        this.mShops = mShops;
        mContext = context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_card, parent,
                    false);
            return new VHItem(v);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      if(holder instanceof VHItem){
            VHItem item = (VHItem)holder;

        }


    }


    @Override
    public int getItemCount() {
        return 0;
    }


    public static class VHItem extends RecyclerView.ViewHolder {

        // Declaration of member variables

        VHItem(View itemView) {
            super(itemView);


        }
    }




}
