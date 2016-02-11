package com.teenvan.newstartup.Api;

import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.Model.Shops;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by navneet on 10/02/16.
 */
public interface BridgeApi {

    @GET("shops")
    Call<ArrayList<Shop>> loadShops();
}
