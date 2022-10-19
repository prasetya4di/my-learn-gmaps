package com.pras.mylearngmaps;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private RadioGroup rgTopLocation;
    private LatLng locStiki, locAlunAlun, locMuseumBrawijaya, locMatos, locMog;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rgTopLocation.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id) {
                case R.id.rbStiki:
                    moveCamera(locStiki);
                    break;
                case R.id.rbAlunAlun:
                    moveCamera(locAlunAlun);
                    break;
                case R.id.rbMuseumBrawijaya:
                    moveCamera(locMuseumBrawijaya);
                    break;
                case R.id.rbMatos:
                    moveCamera(locMatos);
                    break;
                case R.id.rbMog:
                    moveCamera(locMog);
                    break;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Add a marker on my home and move the camera
        LatLng myHome = new LatLng(-8.0122842, 112.6274347);
        gMap.addMarker(new MarkerOptions().position(locStiki));
        gMap.addMarker(new MarkerOptions().position(locAlunAlun));
        gMap.addMarker(new MarkerOptions().position(locMuseumBrawijaya));
        gMap.addMarker(new MarkerOptions().position(locMatos));
        gMap.addMarker(new MarkerOptions().position(locMog));
        gMap.addMarker(new MarkerOptions()
                .position(myHome)
                .title("Marker on myHome"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 15));
    }

    private void moveCamera(LatLng loc) {
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
    }
}
