package com.example.alexander.hikebulgaria.map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.alexander.hikebulgaria.MainActivity;
import com.example.alexander.hikebulgaria.R;
import com.example.alexander.hikebulgaria.SplashScreen;
import com.example.alexander.hikebulgaria.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.android.PolyUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    // GooglePlayServicesClient.ConnectionCallbacks
    // GooglePlayServicesClient.OnConnectionFailedListener

    private GoogleMap mMap;
    private TrackGPS tracker;
    double latitude, longitude, altitude;

    private List<LatLng> path;
    private String pathString;

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

        getLocationInfo();

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
        drawPath(googleMap, pathString);
    }

    private void drawPath(GoogleMap gm, String encodedPath) {
        List<LatLng> pathToDraw = PolyUtil.decode(encodedPath);

        Polyline line = gm.addPolyline(new PolylineOptions().width(3).color(Color.RED));
        line.setPoints(pathToDraw);
    }

    public void setUpMap(GoogleMap googleMap) {
        LatLng currLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(currLocation).title("Marker in CurrLocation"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));

        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        addMarkers(googleMap);
        pathString =  PolyUtil.encode(path);

    }

    private void addMarkers(GoogleMap googleMap) {
        HashMap<LatLng, String> huts = new HashMap<>();
        path = new ArrayList<LatLng>();

        LatLng gorskaHija = new LatLng(42.68829, 27.55074);
        LatLng turisticheskaSpalnqKozichina = new LatLng(42.838373, 27.573294);
        LatLng hijaTopchijsko = new LatLng(42.862150, 27.343883);
        LatLng hijaLudaKamchiq = new LatLng(42.905258, 27.190056);
        LatLng hijaTicha = new LatLng(43.0891694, 26.7851416);
        LatLng turisticheskaSpalnqNeikovo = new LatLng(42.810018, 26.368123);
        LatLng hijaChumerna = new LatLng(42.786876, 25.960662);
        LatLng hijaBukovets = new LatLng(42.789060, 25.887294);
        LatLng hijaMoruley = new LatLng(42.521594, 25.747712);
        LatLng hijaBuzludzaStara = new LatLng(42.734849, 25.395198);
        LatLng hijaBachoKiro = new LatLng(42.947119, 25.429796);
        LatLng gorskiDomBylgarka = new LatLng(42.76472, 25.50310);
        LatLng turisticheskaSpalnqKrystec = new LatLng(42.770275, 25.549856);
        LatLng hijaPredela = new LatLng(42.796434, 25.673701);
        LatLng hijaGramadliva = new LatLng(42.792610, 25.658416);
        LatLng hijaSkiBazaGramadliva = new LatLng(42.79393, 25.65488);

        path.add(gorskaHija);
        path.add(turisticheskaSpalnqKozichina);
        path.add(hijaTopchijsko);
        path.add(hijaLudaKamchiq);

        huts.put(gorskaHija, "х. Горска Хижа, 200м"); //etc.
        huts.put(turisticheskaSpalnqKozichina, "Туристическа спалня, с.Козичина, 524м");
        huts.put(hijaTopchijsko, "х. Топчийско, 430м");
        huts.put(hijaLudaKamchiq, "х. Луда Камчия, 300м");
        huts.put(hijaTicha, "х. Тича, 195");
        huts.put(turisticheskaSpalnqNeikovo, "Туристическа спалня, с. Нейково, 400м");
        huts.put(hijaChumerna, "х. Чумерна, 1446м.");
        huts.put(hijaBukovets, "х. Буковец, 1110м.");
        huts.put(hijaMoruley, "х. Морулей, 595м.");
        huts.put(hijaBuzludzaStara ,"х. Бузлуджа(стара), 1280м.");
        huts.put(hijaBachoKiro, "х. Бачо Киро, 293м.");
        huts.put(gorskiDomBylgarka, "Горски дом Българка, 1135М.");
        huts.put(turisticheskaSpalnqKrystec, "Туристическа спалня гара Кръстец, 912м.");
        huts.put(hijaPredela, "х. Предела, 698м.");
        huts.put(hijaGramadliva ,"х. Грамадлива, 876м.");
        huts.put(hijaSkiBazaGramadliva, "х. Ски база Грамадлива ,860м.");

        for (LatLng coords: huts.keySet()) {
            googleMap.addMarker(new MarkerOptions().position(coords).title(huts.get(coords)));
        }
    }

    public void setUpViewToggle(ToggleButton toggle, final GoogleMap gm) {
        if (gm.getMapType() == GoogleMap.MAP_TYPE_HYBRID) {
            toggle.setChecked(true);
        } else if (gm.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            toggle.setChecked(false);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gm.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else {
                    gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
    }

    public void showSettingsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.settings);

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

    private void getLocationInfo() {
        tracker = new TrackGPS(MapsActivity.this);

        if (tracker.canGetLocation()) {
            longitude = tracker.getLongitude();
            latitude = tracker.getLatitude();
            altitude = tracker.getAltitude();
        } else {
            tracker.showSettingsAlert();
        }
    }

    private void openWebsite(String urlAddr) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(urlAddr));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_altitude) {
            getLocationInfo();
            Toast.makeText(this, getString(R.string.cur_lon) + longitude + "\n" +
                    getString(R.string.cur_lat) + latitude + "\n" +
                    getString(R.string.cur_alt) + altitude, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_distance) {
        } else if (id == R.id.nav_weather) {
            openWebsite(getString(R.string.weather_url));
        } else if (id == R.id.nav_transport) {
            openWebsite(getString(R.string.transport_url));
        } else if (id == R.id.nav_calories) {
        } else if (id == R.id.nav_music) {
            if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWICH_MR1) {
                Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,
                        Intent.CATEGORY_APP_MUSIC);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
                startActivity(intent);
            }
        } else if (id == R.id.nav_offline_maps) {

        } else if (id == R.id.nav_settings) {
            showSettingsDialog();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.stopUsingGPS();
    }
}
