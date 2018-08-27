//package com.daralmathour.altaefhessan.Utils;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//
///**
// * Created by mohamed_3ntar on 2/20/2018.
// */
//
//public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    private Context mContext;
//    private GoogleApiClient mGoogleApiClient;
//    private Location mLastLocation;
//
//    public LocationManager(Context context) {
//        mContext = context;
//        //
//        if (checkIfGooglePlayServicesAreAvailable()) {
//            //Get Access to the google service api
//            buildGoogleApiClient();
//            mGoogleApiClient.connect();
//        } else {
//            //Use Android Location Services
//            //TODO:
//        }
//    }
//
//    public Location getCoarseLocation() {
//        if (mLastLocation != null) {
//            return mLastLocation;
//        } else return null;
//    }
//
//    private synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//    }
//
//    private boolean checkIfGooglePlayServicesAreAvailable() {
//        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
//        if (errorCode != ConnectionResult.SUCCESS) {
//            GooglePlayServicesUtil.getErrorDialog(errorCode, (Activity) mContext, 0).show();
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            return;
//        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (location != null) {
//            mLastLocation = location;
//            Log.d("Location", location.getLongitude() + " , " + location.getLatitude() + " : " + location.getAccuracy());
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        //Toast.makeText(mContext, "suspended", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//}