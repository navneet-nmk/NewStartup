package com.teenvan.newstartup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;

import java.util.ArrayList;

/**
 * Created by navneet on 30/10/15.
 */
public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Declaration of member variables
    private Context mContext;
    private ArrayList<Shop> mShops;

    public ShopListAdapter(ArrayList<Shop> mShops,
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

            item.mShopName.setText(mShops.get(position).getName());
            if(mShops.get(position).getDistance() != 0.0){
                item.mShopDistance.setText(mShops.get(position).getDistance()+ " Km");
            }else{
                item.mShopDistance.setText("Far away");
            }

            item.mShopPoints.setText(mShops.get(position).getPoints()+ " Walk-In Points");
          Glide.with(mContext).load(mShops.get(position).getImageUrl()).into(item.mShopImage);
        }


    }


    @Override
    public int getItemCount() {
        return mShops.size();
    }


    public static class VHItem extends RecyclerView.ViewHolder {

        // Declaration of member variables
        private TextView mShopName;
        private TextView mShopDistance;
        private TextView mShopPoints;
        private ImageView mShopImage;

        VHItem(View itemView) {
            super(itemView);
            mShopName = (TextView)itemView.findViewById(R.id.storeText);
            mShopDistance = (TextView)itemView.findViewById(R.id.storeDistanceText);
            mShopPoints = (TextView)itemView.findViewById(R.id.storePointsText);
            mShopImage = (ImageView)itemView.findViewById(R.id.storeImage);

        }
    }







}
