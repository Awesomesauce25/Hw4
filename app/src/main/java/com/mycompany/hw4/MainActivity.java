package com.mycompany.hw4;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.*;
import android.content.Intent;

import android.app.Activity;
import android.os.Bundle;
import android.location.Geocoder;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import android.widget.*;
import android.location.*;


public class MainActivity extends ActionBarActivity
{
    public final static String LOCATION_MESSAGE = "com.company.hw4.LOCATION";
    private Geocoder coder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coder = new Geocoder(getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    public void sendAddress(View view)
    {
       // Intent intent =new Intent(this,geoActivity.class);
      //  startActivity(intent);

        EditText location = (EditText)findViewById(R.id.addressText);
        //int queryCount = ((SeekBar)findViewById(R.id.queryCount)).getProgress() + 1;
        String locationQuery = location.getText().toString();
        if (coder.isPresent()) {
            ArrayList<Address> locations = new ArrayList<Address>();
            try {
                locations.addAll(coder.getFromLocationName(locationQuery, 50));
            } catch (IOException e) {
            }
            if (locations.size() > 0) {
                Intent intent = new Intent(getBaseContext(), Maps.class);
                intent.putParcelableArrayListExtra(LOCATION_MESSAGE, locations);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Unable to find any matching locations.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No Geocoder implementation exists..", Toast.LENGTH_LONG).show();
        }



    }




    public void viewMaps(View view)
    {
        Intent intent = new Intent(this,Maps.class);
        startActivity(intent);
    }

}
