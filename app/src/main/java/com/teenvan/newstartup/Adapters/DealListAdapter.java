package com.teenvan.newstartup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teenvan.newstartup.Model.Deal;
import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;

import java.util.ArrayList;

/**
 * Created by navneet on 30/10/15.
 */
public class DealListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Declaration of member variables
    private Context mContext;
    private ArrayList<Deal> mDeals;

    public DealListAdapter(ArrayList<Deal> mDeals,
                           Context context){
        this.mDeals = mDeals;
        mContext = context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_list_card, parent,
                    false);
            return new VHItem(v);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      if(holder instanceof VHItem){
            VHItem item = (VHItem)holder;
            item.mDealName.setText(mDeals.get(position).getDealStore());
            item.mDealDesc.setText(mDeals.get(position).getDealDescription());
        }


    }


    @Override
    public int getItemCount() {
        return mDeals.size();
    }


    public static class VHItem extends RecyclerView.ViewHolder {

        // Declaration of member variables
        private TextView mDealName;
        private TextView mDealDesc;
        private ImageView mDealImage;

        VHItem(View itemView) {
            super(itemView);

            //Referencing the UI elements
            mDealName = (TextView)itemView.findViewById(R.id.dealText);
            mDealDesc = (TextView)itemView.findViewById(R.id.dealDescText);
            mDealImage = (ImageView) itemView.findViewById(R.id.dealImage);


        }
    }







}
