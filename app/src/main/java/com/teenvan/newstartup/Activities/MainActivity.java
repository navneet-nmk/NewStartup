package com.teenvan.newstartup.Activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.nearby.messages.devices.NearbyDevice;
import com.teenvan.newstartup.Api.BridgeApi;
import com.teenvan.newstartup.Fragments.DealsFragment;
import com.teenvan.newstartup.Fragments.FirstFragment;
import com.teenvan.newstartup.Fragments.ShopFragment;
import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, TabLayout.OnTabSelectedListener, Callback<ArrayList<Shop>> {

    // Declaration of member variables
    private GoogleApiClient mGoogleApiClient;
    private final String TAG = "Bridge.MainActivity";
    private boolean mResolvingError = false;
    private static final int REQUEST_RESOLVE_ERROR = 100;
    private static final int REQUEST_PERMISSION = 42;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private static final String baseUrl = "https://bridge-startup.herokuapp.com/api/";
    private Double lat = 26.67,longi= 78.75;
    private BridgeApi bridgeApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       // Referencing the UI elements
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);


        setSupportActionBar(mToolbar);
        // Setting the tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Stores"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Deals"));

        mTabLayout.setOnTabSelectedListener(this);



        //setup cache
        File httpCacheDirectory = new File(getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder().cache(cache).
                addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).build();




        //Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bridgeApi= retrofit.create(BridgeApi.class);
        if(lat ==null || longi == null){
            Toast.makeText(this,"Enable location to get nearby stores",Toast.LENGTH_SHORT)
                    .show();
        }else {
            Call<ArrayList<Shop>> call = bridgeApi.loadShops(lat,longi);
            call.enqueue(this);
        }



        // Initializing and adding fragments
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,new ShopFragment()).commit();



    }

    private MessageListener mMessageListener = new MessageListener() {
        @Override
        public void onFound(Message message) {
            Log.d(TAG, message.toString());
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d(TAG, "GoogleAPi Client Connected");

        foregorundSubscribeBeacons();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

            Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(l!=null)
            Log.d(TAG, l.getLatitude() + " "+ l.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Api Connection Suspended : " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "GoogleApi Connection failed : " + connectionResult.getErrorMessage());
    }

    public void foregorundSubscribeBeacons() {
        // Subscribe to receive messages
        Log.i(TAG, "Trying to subscribe");
        if (!mGoogleApiClient.isConnected()) {
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else {
            SubscribeOptions options = new SubscribeOptions.Builder()
                    .setStrategy(Strategy.BLE_ONLY)
                    .setCallback(new SubscribeCallback() {
                        @Override
                        public void onExpired() {
                            Log.i(TAG, "No longer subscribing.");
                        }
                    }).build();

            Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener, options)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.isSuccess()) {
                                Log.i(TAG, "Subscribed successfully.");
                            } else {
                                Log.i(TAG, "Could not subscribe.");
                                // Check whether consent was given;
                                // if not, prompt the user for consent.
                                handleUnsuccessfulNearbyResult(status);
                            }
                        }
                    });

        }
    }

    private void handleUnsuccessfulNearbyResult(Status status) {
        Log.i(TAG, "Processing error, status = " + status);
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (status.hasResolution()) {
            try {
                mResolvingError = true;
                status.startResolutionForResult(this,
                        REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mResolvingError = false;
                Log.i(TAG, "Failed to resolve error status.", e);
            }
        } else {
            if (status.getStatusCode() == CommonStatusCodes.NETWORK_ERROR) {
                Toast.makeText(this.getApplicationContext(),
                        "No connectivity, cannot proceed. Fix in 'Settings' and try again.",
                        Toast.LENGTH_LONG).show();
            } else {
                // To keep things simple, pop a toast for all other error messages.
                Toast.makeText(this.getApplicationContext(), "Unsuccessful: " +
                        status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RESOLVE_ERROR) {
            // User was presented with the Nearby opt-in dialog and pressed "Allow".
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Execute the pending subscription and publication tasks here.
                foregorundSubscribeBeacons();
            } else if (resultCode == RESULT_CANCELED) {
                // User declined to opt-in. Reset application state here.
            } else {
                Toast.makeText(this, "Failed to resolve error with code " + resultCode,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Initiate connection to Play Services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        //The location permission is required on API 23+ to obtain BLE scan results
        int result = ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result != PackageManager.PERMISSION_GRANTED) {
            //Ask for the location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Tear down Play Services connection
        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "Un-subscribing…");

            //mGoogleApiClient.disconnect();
        }
    }

    private PendingIntent getPendingIntent() {
        return PendingIntent.getService(getApplicationContext(), 0,
                getBackgroundSubscribeServiceIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private Intent getBackgroundSubscribeServiceIntent() {
        return new Intent(getApplicationContext(), BackgroundSubscribeIntentService.class);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                transaction.replace(R.id.fragment_container, new ShopFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                Log.d(TAG, "Stores selected");
                break;
            case 1:
                transaction.replace(R.id.fragment_container, new DealsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                Log.d(TAG, " Deals selected");
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    @Override
    public void onResponse(Call<ArrayList<Shop>> call, Response<ArrayList<Shop>> response) {

    }

    @Override
    public void onFailure(Call<ArrayList<Shop>> call, Throwable t) {

    }


    private final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable()) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }

    };

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
             getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
