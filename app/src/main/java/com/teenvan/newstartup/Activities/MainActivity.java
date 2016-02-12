package com.teenvan.newstartup.Activities;

import android.Manifest;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import com.teenvan.newstartup.Model.Deal;
import com.teenvan.newstartup.Model.Shop;
import com.teenvan.newstartup.R;
import com.teenvan.typefacelibrary.TypefaceSetter;

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
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        TabLayout.OnTabSelectedListener, Callback<ArrayList<Shop>> {

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
    private ArrayList<Shop> mShops = new ArrayList<>();
    private ArrayList<Deal> mDeals = new ArrayList<>();
    private int tabPosition = 0;



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

        //Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bridgeApi= retrofit.create(BridgeApi.class);

        Call<ArrayList<Deal>> call = bridgeApi.loadDeals();
        call.enqueue(new Callback<ArrayList<Deal>>() {
            @Override
            public void onResponse(Call<ArrayList<Deal>> call, Response<ArrayList<Deal>> response) {
                mDeals = response.body();
                if(tabPosition==1) {

                    DealsFragment fragment = new DealsFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("Deals", mDeals);
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Deal>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });


        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("Shops", mShops);
        fragment.setArguments(args);

        // Initializing and adding fragments
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,fragment).commit();



    }

    private MessageListener mMessageListener = new MessageListener() {
        @Override
        public void onFound(Message message) {
            // Show a dialog to the User
            Dialog d = new Dialog(MainActivity.this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_layout);
            d.show();
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
            if(l!=null){
                lat =l.getLatitude();
                longi = l.getLongitude();
                    Call<ArrayList<Shop>> call = bridgeApi.loadShops(lat,longi);
                    call.enqueue(this);

            }

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

            mGoogleApiClient.disconnect();

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
                ShopFragment fragment = new ShopFragment();
                Bundle args = new Bundle();
                args.putParcelableArrayList("Shops", mShops);
                fragment.setArguments(args);
                tabPosition = 0;
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                DealsFragment dealsFragment = new DealsFragment();
                Bundle dealArgs = new Bundle();
                dealArgs.putParcelableArrayList("Deals", mDeals);
                dealsFragment.setArguments(dealArgs);
                tabPosition = 1;
                transaction.replace(R.id.fragment_container, dealsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
        mShops =new ArrayList<>(response.body().size());
        mShops = response.body();
        if(tabPosition==0) {

            ShopFragment fragment = new ShopFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("Shops", mShops);
            fragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Shop>> call, Throwable t) {
        Log.d(TAG, t.getMessage());
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
             getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
