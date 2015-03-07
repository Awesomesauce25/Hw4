package com.mycompany.hw4;

import android.content.Intent;
import android.location.Address;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


public class Maps extends FragmentActivity
{

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        //createMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
       // map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // createMap();


    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #map} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

     /*
     * Copyright (C) 2012 The Android Open Source Project
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null)
            {
                Intent intent= getIntent();

                if(intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0)==4)
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                if(intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0)==3)
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                if(intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0)==2)
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                setUpMap();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpMap()
    {
        Intent intent = getIntent();
        ArrayList<Parcelable> addresses = (ArrayList<Parcelable>) intent.getExtras().getParcelableArrayList(MainActivity.LOCATION_MESSAGE);
        LatLng location = new LatLng(0, 0);
        PolylineOptions polyOpt = new PolylineOptions().geodesic(true).color(0xDDEE3333);

        for (Parcelable parcel : addresses)
        {
            Address address = (Address) parcel;
            location = new LatLng(address.getLatitude(), address.getLongitude());
            polyOpt.add(location);

            String marker;
            if (address.getFeatureName() != null)
            {
                marker = address.getFeatureName();
            } else if (address.getAddressLine(0) != null)
            {
                marker = address.getAddressLine(0);
            } else
            {
                marker = "Marker";
            }

            String snip = address.getLatitude() + "° " + address.getLongitude() + "°";

            map.addMarker(new MarkerOptions().position(location).title(marker).snippet(snip));
        }

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(location, 14, 45, 0)));
        map.addPolyline(polyOpt);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(marker.getPosition(), 14, 45, 0)));
                marker.showInfoWindow();
                return true;
            }
        });
    }
}


