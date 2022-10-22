package com.pras.mylearngmaps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private RadioGroup rgTopLocation;
    private LatLng locStiki, locAlunAlun, locMuseumBrawijaya, locMatos, locMog, currentLoc;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgTopLocation = findViewById(R.id.rgTopLocation);
        locStiki = new LatLng(-7.9662032, 112.6076907);
        locAlunAlun = new LatLng(-7.9824631, 112.63088185731061);
        locMuseumBrawijaya = new LatLng(-7.9719815, 112.6208468);
        locMatos = new LatLng(-7.9568425000000005, 112.61874174035411);
        locMog = new LatLng(-7.9769603, 112.6238642);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rgTopLocation.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id) {
                case R.id.rbStiki:
                    checkDistance(locStiki, getString(R.string.stiki_text));
                    moveCamera(locStiki);
                    break;
                case R.id.rbAlunAlun:
                    checkDistance(locAlunAlun, getString(R.string.alun_alun_text));
                    moveCamera(locAlunAlun);
                    break;
                case R.id.rbMuseumBrawijaya:
                    checkDistance(locMuseumBrawijaya, getString(R.string.museum_brawijaya_text));
                    moveCamera(locMuseumBrawijaya);
                    break;
                case R.id.rbMatos:
                    checkDistance(locMatos, getString(R.string.matos_text));
                    moveCamera(locMatos);
                    break;
                case R.id.rbMog:
                    checkDistance(locMog, getString(R.string.mog_text));
                    moveCamera(locMog);
                    break;
            }
        });

        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        gMap.addMarker(new MarkerOptions()
                                        .position(currentLoc)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                        .title("User Current Location"))
                                .showInfoWindow();
                        moveCamera(currentLoc);
                    } else {
                        moveCamera(locStiki);
                    }
                });
    }

    private void checkDistance(@NonNull LatLng location, String locName) {
        double distance = SphericalUtil.computeDistanceBetween(currentLoc, location) / 1000;

        Toast.makeText(this, getString(R.string.distance_message_text, locName, distance), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        gMap.addMarker(new MarkerOptions().position(locStiki));
        gMap.addMarker(new MarkerOptions().position(locAlunAlun));
        gMap.addMarker(new MarkerOptions().position(locMuseumBrawijaya));
        gMap.addMarker(new MarkerOptions().position(locMatos));
        gMap.addMarker(new MarkerOptions().position(locMog));
    }

    private void moveCamera(LatLng loc) {
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }
}
