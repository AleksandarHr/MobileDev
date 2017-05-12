package com.example.alexander.hikebulgaria.map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.alexander.hikebulgaria.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap(mMap);
    }

    public void setUpMap (GoogleMap googleMap) {
        LatLng burgas = new LatLng(42.5048, 27.4626);
        googleMap.addMarker(new MarkerOptions().position(burgas).title("Marker in Burgas"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(burgas));

        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }

    public void setUpViewToggle(ToggleButton toggle, final GoogleMap gm) {
        if (gm.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {toggle.setChecked(true);}
        else if (gm.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {toggle.setChecked(false);}

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gm.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
    }

    public void showSettingsDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.settings, null);
        builder.setView(dialogView);
        ToggleButton toggle = (ToggleButton) dialogView.findViewById(R.id.toggleView);
        setUpViewToggle(toggle, mMap);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_elevation) {
        }
        else if (id == R.id.nav_distance) {
        }
        else if (id == R.id.nav_weather) {
        }
        else if (id == R.id.nav_transport) {
        }
        else if (id == R.id.nav_calories) {
        }
        else if (id == R.id.nav_music) {
            Intent intent=Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MUSIC);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
            startActivity(intent);
        }
        else if (id == R.id.nav_offline_maps) {

        }
        else if (id == R.id.nav_settings) {
            showSettingsDialog();
        }
        else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
